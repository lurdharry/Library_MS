# Library Management System Microservices

A collection of Spring Boot microservices implementing a library management system, featuring:

- **Gateway Service**: API gateway for routing and authentication.
- **Auth Service**: OAuth2 authorization server, user registration, login, JWT issuance.
- **User Service**: Manages user profiles (CRUD) with role-based access control.
- **Book Service**: Manages book catalog (CRUD) on MongoDB.
- **Borrow Service**: Handles borrowing & returning books.
- **Notification Service**: Sends due-date reminders.

---

## Architecture Diagram

![Architecture Diagram](A_diagram_illustrates_a_microservices_architecture.png)

---

## Services Overview

### 0. Service Discovery (`discovery-service`)
- **Type**: Netflix Eureka Server
- **Port**: `8761`
- **Responsibilities**:
    - Maintains registry of all microservices
    - Enables dynamic service discovery for Gateway and inter-service calls
- **Configuration**:
    - Annotated with `@EnableEurekaServer`
    - Services register via `spring.cloud.discovery.client.*` settings

### 1. Gateway Service (`gateway-service`)
- **Type**: Spring Cloud Gateway (Reactive)
- **Port**: `2222`
- **Responsibilities**:
    - Routes requests to backend services using service IDs
    - Validates JWTs via `.well-known/jwks.json`
    - Integrates with Eureka for dynamic routing

### 2. Authentication Service (`auth-service`)
- **Type**: Spring Authorization Server
- **Port**: `8070`
- **Responsibilities**:
    - OAuth2 endpoints: `/oauth2/authorize`, `/oauth2/token`
    - User registration (`/auth/register`), login (`/auth/login`), token refresh
    - Role & permission management (ADMIN, ATTENDANT, USER)
- **Security**:
    - RSA-signed JWTs (RS256)
    - JWKS endpoint: `/.well-known/jwks.json`
    - Method security via `@EnableMethodSecurity` and `@PreAuthorize`

### 3. User Service (`user-service`)
- **Type**: Spring Boot MVC + JPA
- **Port**: `8081`
- **Database**: PostgreSQL (UUID primary keys)
- **Responsibilities**:
    - CRUD operations on users
    - Profile updates, password change (with current-password verification)
- **Security**:
    - JWT resource server configuration with `NimbusJwtDecoder`
    - Method-level security via `@PreAuthorize`
    - Authorities extracted from JWT `roles` claim

### 4. Book Service (`book-service`)
- **Type**: Spring Boot MVC + Spring Data MongoDB
- **Port**: `8082`
- **Database**: MongoDB
- **Responsibilities**:
    - CRUD operations on books
    - Maintain availability counts
- **Security**:
    - JWT resource server
    - Protected endpoints via `@PreAuthorize` (e.g., `hasAuthority('USER_BORROW_BOOK')`)

### 5. Borrow Service (`borrow-service`)
- **Type**: Spring Boot MVC + JPA
- **Port**: `8083`
- **Database**: PostgreSQL
- **Responsibilities**:
    - Handle borrow/return requests
    - Update book availability via events or REST calls
- **Security**:
    - JWT resource server
    - Role-based approval: `ATTENDANT` approves borrow requests

### 6. Notification Service (`notification-service`)
- **Type**: Spring Boot MVC + Scheduler
- **Responsibilities**:
    - Schedule and send reminders for due dates
    - Integrate with email/SMS provider

---

## Inter-Service Communication

- **Event-Driven**: Services publish domain events (e.g., `BookBorrowed`) on a message broker for eventual consistency.
- **Synchronous REST**: Services verify availability or user details via REST calls when immediate consistency is required.

---

## Security & RBAC

- **Roles**:
    - `ROLE_ADMIN`: full system access
    - `ROLE_ATTENDANT`: manage borrowing, fines, inventory
    - `ROLE_USER`: view catalog, borrow/return books
- **Permissions**: granular authorities assigned per role (e.g., `ADMIN_MANAGE_USERS`, `ATTENDANT_CHECKOUT_BOOK`, `USER_RATE_BOOK`)
- **JWT**:
    - RS256-signed tokens
    - `roles` claim contains list of authorities
    - Gateway and resource servers use JWKS endpoint for key discovery

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL
- MongoDB

### Running Locally

1. **Start databases**:
   ```bash
    docker compose down && docker compose up -d

