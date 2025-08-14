package pl.doublecodestudio.nexuserp.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
public class JwtService {
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 8; // 8h
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private SecretKey key;

    @PostConstruct
    public void init() {
        if (SECRET_KEY == null || SECRET_KEY.isBlank()) {
            throw new IllegalStateException("jwt.secret is missing. Provide application-docker.yml or env JWT_SECRET.");
        }
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) { // 256 bits
            throw new IllegalStateException("jwt.secret must be at least 32 bytes for HS256 (jjwt).");
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        log.info("JWT secret configured (length: {})", keyBytes.length);
    }


    public String generateToken(String subject, Set<String> roles) {
        return Jwts.builder()
                .subject(subject)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractSubject(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Set<String> extractRoles(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Collection<?> rawRoles = (Collection<?>) claims.get("roles");

        return rawRoles.stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
