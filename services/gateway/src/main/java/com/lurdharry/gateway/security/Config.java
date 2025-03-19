package com.lurdharry.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;


@Configuration
@EnableWebFluxSecurity
public class Config {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtUtil jwtUtil) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // We use a stateless security context
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchange -> exchange
                        // Permit public endpoints (if any)
                        .pathMatchers("/auth/register", "/auth/login").permitAll()
                        // All other endpoints must be authenticated
                        .anyExchange().authenticated()
                );
                // Add our custom JWT filter at the authentication phase
        http.addFilterAt(new JwtAuthFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }


}
