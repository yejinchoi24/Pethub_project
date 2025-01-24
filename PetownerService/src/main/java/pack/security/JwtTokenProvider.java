package pack.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import pack.config.JwtConfig;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(JwtConfig jwtConfig) {
    	String secretKey = jwtConfig.getJwtSecret();
        if (secretKey == null || secretKey.length() < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 32 characters long");
        }
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(String userId, String role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1시간 유효
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // Subject(userId) 반환
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Token expired", e);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }
}
