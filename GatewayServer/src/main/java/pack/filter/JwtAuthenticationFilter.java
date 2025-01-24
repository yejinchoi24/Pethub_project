package pack.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter {
	
    private final JWTVerifier jwtVerifier;

    public JwtAuthenticationFilter(@Value("${jwt.secret}") String jwtSecret) {
    	System.out.println("!!! 시크릿키 : " + jwtSecret);
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret); // PetownerService와 동일한 Secret Key
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 인증이 필요 없는 경로는 필터를 건너뛰기
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/api/petowner/email-check") 
        		|| path.startsWith("/api/petowner/signup") 
        		|| path.startsWith("/api/auth/login")
        		
        		|| path.startsWith("/api/reviews2")
    	        || path.startsWith("/api/reviews2/rankings")
    	        || path.startsWith("/hospitals")
    	        || path.startsWith("/hospitals/{id}")
    	        || path.startsWith("/hospitals/emergency-hospitals") // 추가된 경로: 모든 응급 병원 정보
    	        || path.startsWith("/hospitals/emergency-hospitals/24-hour") // 추가된 경로: 24시간 병원만
    	        || path.startsWith("/hospitals/emergency-hospitals/region")

    	        || path.startsWith("/api/pets")  // PetService 관련 경로
                || path.startsWith("/api/pets/{petId}")
                || path.startsWith("/api/pets/owner/{petownerId}")
                || path.startsWith("/api/pets/owner/details/{petownerId}")
    	        || path.startsWith("/api/vaccination")       // Vaccination 전체 경로
    	        || path.startsWith("/api/vaccination/{id}")  // 특정 ID로 조회
    	        || path.startsWith("/api/vaccination/add")   // 백신 생성
    	        || path.startsWith("/api/vaccination/delete/{id}")  // 백신 삭제

    	        
//    	        || path.startsWith("/api/chatting")
//    	        || path.startsWith("/api/chatting/recomment")
    	        
    	        || path.startsWith("/actuator")

        	) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange.getRequest());
        log.info("Extracted Authorization Header Token: {}", token);
        System.out.println("!! JWTAuthentication 필터 - Extracted Authorization 헤더토큰 : " + token);
        
        if (token == null) {
        	System.out.println("!! JWTAuthentic필터 => 토큰 NULL");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        if (!isValidToken(token)) {
        	System.out.println("!! JWTAuthentic필터 => Validation 결과 : false");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        System.out.println("!! JWT Validation 결과 : true");

        String userId = getUserIdFromToken(token);
        
        // -----------------------------------
//        exchange.getRequest().mutate()
//                .header("X-User-Id", userId)
//                .build();
        if (isServiceToken(token)) {
            log.info("Service Token Validated");
            exchange.getRequest().mutate().header("X-Service", "true").build();
        } else {
            exchange.getRequest().mutate().header("X-User-Id", userId).build();
        }
        // -----------------------------------
        
        
        // Authorization 헤더 확인 
        log.info("Forwarding Authorization header: {}", exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        return chain.filter(exchange);
    }
    
    // -----------------------------------
    private boolean isServiceToken(String token) {
        // 특정 claim 값, subject, 또는 secret key를 확인하여 서비스 토큰 검증
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            return "internal-service".equals(jwt.getSubject());
        } catch (Exception e) {
            return false;
        }
    }
    // -----------------------------------

    private String extractToken(org.springframework.http.server.reactive.ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    private boolean isValidToken(String token) {
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            log.info("Valid token for user: {}", jwt.getSubject());
            return true;
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            return false;
        }
    }


    private String getUserIdFromToken(String token) {
        return jwtVerifier.verify(token).getSubject();
    }
}

