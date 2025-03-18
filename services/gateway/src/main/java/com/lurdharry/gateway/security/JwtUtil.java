package com.lurdharry.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {
    @Value("${jwt.secret:#{systemEnvironment['JWT_SECRET']}}")
    private static String SECRET_KEY;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getPayload (String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return getPayload(token).getSubject();
    }

    public String extractRole (String token){
        return getPayload(token).get("role", String.class);
    }

    public boolean validateToken(String token){
        try {
            Claims claims = getPayload(token);
            return claims.getSubject() != null && claims.getExpiration().after(new Date());
        } catch (JwtException e) {
            return false;
        }
    }
}
