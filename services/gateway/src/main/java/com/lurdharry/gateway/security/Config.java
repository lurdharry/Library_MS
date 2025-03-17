package com.lurdharry.gateway.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.oauth2.jwt.Jwt;



import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class Config {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity security) throws Exception {
        security
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange-> exchange
                        .pathMatchers("/auth/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2->
                        oauth2.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return security.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter((jwt)->{
            Collection<String> roles = jwt.getClaimAsStringList("role");
        });
    }

    private Collection<GrantedAuthority> extractRoles(Jwt jwt){
        Object roles = jwt.getClaim("role");
        if (roles instanceof  String){
            return List.of(new SimpleGrantedAuthority("ROLE_"+roles))
        } else if (roles  instanceof Coll)
    }


}
