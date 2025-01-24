package pack.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pack.dto.PetownerDTO;
import pack.feign.PetownerFeignClient;
//import pack.service.PetownerService;
import pack.util.JwtUtil;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class PetownerController {
	
	@Autowired
    private JwtUtil jwtUtil; // JwtUtil 의존성 주입
    
//    @Autowired
//    private PetownerService petownerService;
    
    @Autowired
    private PetownerFeignClient petownerFeignClient;
    
    
//    @GetMapping("")
//    public ResponseEntity<?> getMyPageInfo(@RequestHeader("Authorization") String token) {
////    	log.info("마이페이지 Authorization Header: {}", token);
//    	System.out.println("마이페이지 Authorization Header: " + token);
//
//    	try {
//            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", "")); // 토큰 검증 및 이메일 추출
//            System.out.println("리뷰컨트롤러 petownerId : " + petownerId);
//            PetownerDTO petownerDTO = petownerService.findById(Long.parseLong(petownerId));
//            System.out.println("리뷰컨트롤러 PetownerDTO: " + petownerDTO);
//            
//            if (petownerDTO == null) {
//            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
//            }
//            return ResponseEntity.ok(Map.of("name", petownerDTO.getName()));
//            
//        } catch (Exception e) {
//        	System.out.println("리뷰컨트롤러 토큰검증실패 : " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token", "details", e.getMessage()));
//        }
//    }
    
    
    @GetMapping("")
    public ResponseEntity<?> getMyPageInfo(@RequestHeader("Authorization") String token) {
    	System.out.println("마이페이지 Feign 관련 => 토큰 : " + token);
        try {
            // JWT 토큰에서 사용자 ID 추출
            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
            System.out.println("마이페이지 컨트롤러 petownerId : " + petownerId);

            // FeignClient로 PetownerService 호출
            PetownerDTO petownerDTO = petownerFeignClient.getPetownerById(Long.parseLong(petownerId), token);

            if (petownerDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
            }

            return ResponseEntity.ok(Map.of("name", petownerDTO.getName()));
        } catch (Exception e) {
            System.out.println("FeignClient 호출 실패 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden Access");
        }
    }

    
    // =============================================================================================
    
    
//    @GetMapping("/info")
//    public ResponseEntity<?> getInfo(@RequestHeader("Authorization") String token) {
//    	try {
//            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", "")); // 토큰 검증 및 이메일 추출
//            PetownerDTO petownerDTO = petownerService.findById(Long.parseLong(petownerId)); // 이메일로 회원 조회
//
//            if (petownerDTO == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
//            }
//
//            // PetownerDTO 데이터를 반환
//            return ResponseEntity.ok(petownerDTO);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token", "details", e.getMessage()));
//        }
//    }
    
    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@RequestHeader("Authorization") String token) {
        System.out.println("Info Feign 관련 => 토큰 : " + token);

        try {
            // JWT 토큰에서 사용자 ID 추출
            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
            System.out.println("마이페이지 컨트롤러 petownerId : " + petownerId);

            // FeignClient로 PetownerService 호출
            PetownerDTO petownerDTO = petownerFeignClient.getPetownerById(Long.parseLong(petownerId), token);

            if (petownerDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
            }

            // PetownerDTO 데이터를 반환
            return ResponseEntity.ok(petownerDTO);
        } catch (Exception e) {
            System.out.println("FeignClient 호출 실패 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Invalid token", "details", e.getMessage()));
        }
    }
    
    
    // =============================================================================================
    
    
//    @PutMapping("/update")
//    public ResponseEntity<?> updateUserInfo(@RequestHeader("Authorization") String token, @RequestBody PetownerDTO updatedPetownerDTO) {
//        try {
//            // 토큰 검증 및 사용자 ID 추출
//            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
//            System.out.println("업데이트 - 리뷰 컨트롤러 petownerId : " + petownerId);
//
//            // ID로 사용자 조회
//            updatedPetownerDTO.setPetownerId(Long.parseLong(petownerId));
//
//            petownerService.update(updatedPetownerDTO);
//
//            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
//        } catch (Exception e) {
//            System.out.println("업데이트 컨트롤러 에러 : " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
//        }
//    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestHeader("Authorization") String token, @RequestBody PetownerDTO updatedPetownerDTO) {
        System.out.println("업데이트 Feign 관련 => 업데이트 요청 토큰 : " + token);
        System.out.println("Update => 데이터: " + updatedPetownerDTO);

        try {
            // JWT 토큰에서 사용자 ID 추출
            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
            System.out.println("업데이트 - 마이페이지 컨트롤러 petownerId : " + petownerId);

            // FeignClient로 PetownerService 호출하여 사용자 정보 업데이트
            updatedPetownerDTO.setPetownerId(Long.parseLong(petownerId)); // 토큰에서 추출한 petownerId 설정
            petownerFeignClient.update(updatedPetownerDTO, token);

            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            System.out.println("FeignClient 업데이트 호출 실패 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
