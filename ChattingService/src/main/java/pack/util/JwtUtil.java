package pack.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {

    private final Key key;

    // 생성자에서 application.yml에서 비밀 키를 주입받습니다.
    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        if (secretKey == null || secretKey.length() < 32) {
            throw new IllegalArgumentException("JwtUtil 시크릿 키");
        }
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * JWT 생성
     *
     * @param email 사용자 이메일 (토큰의 Subject)
     * @param claims 사용자 정의 클레임
     * @param expirationMs 토큰 유효 시간 (밀리초)
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String email, Map<String, Object> claims, long expirationMs) {
        return Jwts.builder()
                .setSubject(email)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 검증 및 Subject(email) 반환
     *
     * @param token JWT 토큰
     * @return 이메일 (Subject)
     * @throws IllegalArgumentException 유효하지 않은 토큰
     */
    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
            log.info("!! JwtUtil 확인 validated for subject: {}", claims.getSubject());
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.error("# JWT expired at: {}", e.getClaims().getExpiration());
            throw new IllegalArgumentException("# Token expired", e);
        }
        catch (JwtException e) {
            log.error("# Invalid JWT signature: {}", e.getMessage());
            throw new IllegalArgumentException("# Invalid token", e);
        }

    }
}
