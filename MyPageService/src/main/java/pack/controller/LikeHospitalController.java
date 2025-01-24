package pack.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pack.service.LikeService;
import pack.util.JwtUtil;

@RestController
@RequestMapping("/api/likehospital")
@RequiredArgsConstructor
public class LikeHospitalController {

	@Autowired
	LikeService likeService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("")
	public ResponseEntity<?> getLikeStatus(
	        @RequestHeader("Authorization") String token,
	        @RequestParam Long hospitalId
	    ) {
	    try {
	        // JWT로 사용자 ID 확인
	        String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
	        System.out.println("찜 상태 확인 요청 - petownerId: " + petownerId + ", hospitalId: " + hospitalId);

	        // 현재 찜 상태만 반환
	        boolean isLiked = likeService.isLiked(Long.parseLong(petownerId), hospitalId);
	        System.out.println("!! 찜 !! petowner & hospital : " 
            		+ petownerId + " & " + hospitalId
            		+ " => isLiked : " + isLiked);
	        
	        return ResponseEntity.ok(Map.of("liked", isLiked));
	    } catch (Exception e) {
	        System.out.println("찜 상태 확인 중 오류: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body(Map.of("error", "Invalid token", "details", e.getMessage()));
	    }
	}
	

	
	@PostMapping("")
	public ResponseEntity<?> toggleLike(
	        @RequestHeader("Authorization") String token,
	        @RequestParam Long hospitalId
	    ) {
	    try {
	        // JWT에서 사용자 ID 추출
	        String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
	        System.out.println("찜 상태 변경 요청 - petownerId: " + petownerId + ", hospitalId: " + hospitalId);

	        // 찜 상태 토글 처리
	        boolean isLiked = likeService.toggleLike(Long.parseLong(petownerId), hospitalId);
	        return ResponseEntity.ok(Map.of("liked", isLiked));
	    } catch (Exception e) {
	        System.out.println("찜 상태 변경 중 오류: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body(Map.of("error", "Failed to toggle like", "details", e.getMessage()));
	    }
	}

}
