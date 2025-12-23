package com.example.patientrecordsystem.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Service responsible for JWT token generation and parsing.
 *
 * <p>Uses application properties for secret and expiration to sign and validate tokens.
 */
@Service
public class JwtService {
    @Value("${jwt.secret}")

    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    private static final long EXPIRATION_TIME = 86400000;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    /**
     * Generates a JWT token for the given email.
     *
     * @param email the email to encode in the token subject
     * @return the signed JWT token
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the email (subject) from a JWT token.
     *
     * @param token the JWT token
     * @return the email encoded in the token
     */
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }
}

