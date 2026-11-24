package com.pragma.plazoleta.infrastructure.output.feign.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class ServiceTokenProvider {

    private final SecretKey secretKey;

    public ServiceTokenProvider(
            @Value("${app.security.service-jwt.secret}") String secret
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken() {

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 5 * 60 * 1000);

        return Jwts.builder()
                .subject("ms-plazoleta")
                .claim("type", "SERVICE")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }
}