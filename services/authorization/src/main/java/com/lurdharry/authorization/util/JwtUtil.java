package com.lurdharry.authorization.util;

import com.lurdharry.authorization.user.Role;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtUtil {
    @Value("${jwt.secret:#{systemEnvironment['JWT_SECRET']}}")
    private String SECRET_KEY;

    @Value(("${jwt.expiration}"))
    private Long EXPIRY_TIME; // 24 hours


    private SecretKey getSigningKey(){
        System.out.println("shshhs. " + SECRET_KEY);
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, Role role){
        return Jwts.builder()
                .claims(Map.of("sub", email, "role", role.name()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRY_TIME))
                .signWith(getSigningKey())
                .compact();
    }

}
