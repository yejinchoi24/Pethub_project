package pack.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.PetownerDTO;
import pack.security.JwtTokenProvider;
import pack.service.PetownerService;

@RestController
@RequestMapping("/api/petowner") // 사용자 데이터 관리 전용 경로
public class PetownerController {

    private final PetownerService petownerService;
    private final JwtTokenProvider jwtTokenProvider;

    public PetownerController(PetownerService petownerService, JwtTokenProvider jwtTokenProvider) {
        this.petownerService = petownerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup") // 회원가입 경로
    public ResponseEntity<String> signup(@RequestBody PetownerDTO petownerDTO) {
        petownerService.signup(petownerDTO);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/email-check")
    public ResponseEntity<String> emailCheck(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("이메일 체크 요청: " + email); // 디버깅 로그

        if (email == null || email.isEmpty()) {
            System.out.println("유효하지 않은 이메일 입력"); // 디버깅 로그
            return ResponseEntity.badRequest().body("Email is required");
        }

        try {
            String checkResult = petownerService.emailCheck(email);

            if ("ok".equals(checkResult)) {
                System.out.println("이메일 사용 가능: " + email); // 디버깅 로그
                return ResponseEntity.ok("ok");
            } else {
                System.out.println("이메일 중복: " + email); // 디버깅 로그
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 디버깅 로그
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }
    
    // ======================================================================
    
    @GetMapping("/{petownerId}")
    public ResponseEntity<?> getPetownerById(@PathVariable Long petownerId) {
        PetownerDTO petownerDTO = petownerService.findById(petownerId);

        if (petownerDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(petownerDTO);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updatePetowner(@RequestBody PetownerDTO updatedPetownerDTO, @RequestHeader("Authorization") String token) {
        System.out.println("Petowner Service - 업데이트 요청 데이터: " + updatedPetownerDTO);

        try {
            // 서비스의 update 메서드 호출
            petownerService.update(updatedPetownerDTO);
            return ResponseEntity.ok("Update successful");
        } catch (RuntimeException e) {
            System.out.println("Petowner Service - 업데이트 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Petowner Service - 알 수 없는 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "알 수 없는 오류가 발생했습니다."));
        }
    }
}
