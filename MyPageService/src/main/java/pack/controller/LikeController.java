package pack.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pack.dto.LikeDTO;
import pack.service.LikeService;
import pack.util.JwtUtil;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class LikeController {
	
	@Autowired
    JwtUtil jwtUtil; // JwtUtil 의존성 주입
    
    @Autowired
    LikeService likeService;
    
    @GetMapping("/like")
    public ResponseEntity<?> getMyReviews(@RequestHeader("Authorization") String token) {
        try {
            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
            List<LikeDTO> likes = likeService.getLikesByPetownerId(Long.parseLong(petownerId));
            return ResponseEntity.ok(likes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token", "details", e.getMessage()));
        }
    }
}
