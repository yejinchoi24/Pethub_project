package pack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Data
@Component
public class JwtConfig {
	
	@Value("${jwt.secret}")
    private String jwtSecret;
    
    @PostConstruct
    public void validateConfig() {
        if (jwtSecret == null || jwtSecret.length() < 32) {
        	System.out.println("현재 시크릿 키 : " + jwtSecret);
            throw new IllegalArgumentException("JWT 시크릿키는 최소 32자 이상이어야 합니다.");
        }
    }
}