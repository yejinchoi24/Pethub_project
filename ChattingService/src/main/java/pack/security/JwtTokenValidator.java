package pack.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import pack.config.JwtConfig;

@Component
public class JwtTokenValidator {

    private final SecretKey secretKey;

    public JwtTokenValidator(JwtConfig jwtConfig) {
    	String secret = jwtConfig.getJwtSecret();
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("시크릿키");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // SecretKey 생성
    }

    public String validateTokenAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey) // Secret Key 사용
                .build()
                .parseClaimsJws(token) // 토큰 검증
                .getBody();
        return claims.getSubject(); // 토큰에서 사용자 ID 추출
    }
}
