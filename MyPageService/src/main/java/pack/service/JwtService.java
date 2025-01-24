package pack.service;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pack.config.JwtConfig;

@Component
@RequiredArgsConstructor
public class JwtService {
	
    private final JwtConfig jwtConfig;

    public void printJwtSecret() {
        System.out.println("JWT Secret: " + jwtConfig.getJwtSecret());
    }
}