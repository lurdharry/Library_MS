package com.lurdharry.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        System.out.println("🟢 Incoming Request Path: " + request.getURI());


        if (request.getURI().getPath().startsWith("/auth/login") || request.getURI().getPath().startsWith("/auth/register")){
            System.out.println("🟡 Public Route: No JWT validation needed.");

            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")){
            System.out.println("❌ Unauthorized: Missing or invalid Authorization header");
            return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }
         token = token.substring(7);
        System.out.println("🟡 Received JWT Token: " + token);

        if (!jwtUtil.validateToken(token)){
            System.out.println("❌ Unauthorized: Invalid JWT token");
            return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
        }

        String email = jwtUtil.extractEmail(token);
        String role = jwtUtil.extractRole(token);
        System.out.println("🟡 Extracted Email: " + email);
        System.out.println("🟡 Extracted Role: " + role);

        // Create an Authentication token with the email and role
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                email,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );

        exchange.getRequest().mutate()
                .header("X-User-Email", email)
                .header("X-User-Role", role)
                .build();

        System.out.println("✅ JWT is valid. Forwarding request...");
        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl(auth))));
    }

    private Mono<Void> onError(ServerWebExchange exchange, String msg, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

}
