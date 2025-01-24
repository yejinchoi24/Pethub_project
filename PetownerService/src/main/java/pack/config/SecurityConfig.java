package pack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Order(1)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                		"/api/auth/login", // 로그인 요청
                		"/api/petowner/signup", // 회원가입 요청
                		"/api/petowner/email-check" // 이메일 중복 확인 요청
                		
                		, "/api/petowner/**"
                      ).permitAll() // 로그인 요청 인증 없이 허용
                
                
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            )
            .httpBasic(httpBasic -> httpBasic.disable()) // HTTP 기본 인증 비활성화
            .formLogin(formLogin -> formLogin.disable()); // 폼 기반 인증 비활성화

        return http.build();
    }
}

