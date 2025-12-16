package com.cc6.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        // Garante que a chave tenha pelo menos 256 bits (32 bytes) para HMAC-SHA256
        String paddedSecret = secret;
        while (paddedSecret.length() < 32) {
            paddedSecret += paddedSecret;
        }
        // Pega apenas os primeiros 32 bytes para garantir tamanho consistente
        byte[] keyBytes = paddedSecret.substring(0, 32).getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UUID userId, String email, String role) {
        log.debug("Gerando token JWT para usuário: {}", email);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            // Remove "Bearer " prefix if present
            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(cleanToken)
                    .getBody();

            log.debug("Token validado com sucesso para usuário: {}", claims.getSubject());
            return claims;

        } catch (Exception e) {
            log.error("Erro ao validar token: {}", e.getMessage());
            throw new RuntimeException("Token inválido", e);
        }
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public UUID getUserIdFromToken(Claims claims) {
        return UUID.fromString(claims.getSubject());
    }

    public String getEmailFromToken(Claims claims) {
        return claims.get("email", String.class);
    }

    public String getRoleFromToken(Claims claims) {
        return claims.get("role", String.class);
    }
}
