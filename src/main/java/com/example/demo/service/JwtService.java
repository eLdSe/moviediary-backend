package com.example.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    // Секретный ключ — никому не показывай!
    @Value("${jwt.secret}")
    private String SECRET;

    // Создать токен для пользователя
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)           // кому выдан токен
                .issuedAt(new Date())        // когда выдан
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // живёт 24 часа
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))  // подписываем ключом
                .compact();
    }

    // Достать username из токена
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Проверить что токен действителен
    public boolean isValid(String token, String username) {
        return extractUsername(token).equals(username);
    }
}