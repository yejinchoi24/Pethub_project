package pack.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.LoginRequest;
import pack.dto.PetownerDTO;
import pack.security.JwtTokenProvider;
import pack.service.PetownerService;

@RestController
@RequestMapping("/api/auth") // React에서 호출하는 경로와 일치해야 합니다
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final PetownerService petownerService;

    public AuthController(JwtTokenProvider jwtTokenProvider, PetownerService petownerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.petownerService = petownerService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("!! Auth컨트롤러 Login attempt for email: " + loginRequest.getEmail());

        // PetownerService를 통해 사용자 인증
        PetownerDTO petowner = petownerService.login(new PetownerDTO(null, null, null, loginRequest.getEmail(), loginRequest.getPassword()));
        
        if (petowner == null) {
            System.err.println("!! Auth컨트롤러 Authentication failed for email: " + loginRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
        }

        // JWT 생성
        String token = jwtTokenProvider.createToken(String.valueOf(petowner.getPetownerId()), "ROLE_USER");
        System.out.println("!! Auth컨트롤러 token: " + token);

        return ResponseEntity.ok(Map.of("token", token));
    }
}
