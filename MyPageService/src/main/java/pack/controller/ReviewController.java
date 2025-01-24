package pack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pack.dto.AddReviewRequest;
import pack.dto.ReviewDTO;
import pack.entity.Review;
import pack.feign.HospitalFeignClient;
import pack.service.ReviewService;
import pack.util.JwtUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    @Autowired
    JwtUtil jwtUtil; // JwtUtil 의존성 주입
    
    @Autowired
    ReviewService reviewService;
    
    @Autowired
    private final HospitalFeignClient hospitalFeignClient;
    
    // '작성한 리뷰 보기'
    @GetMapping("/reviews")
    public ResponseEntity<?> getMyReviews(@RequestHeader("Authorization") String token) {
        try {
            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
            List<ReviewDTO> reviews = reviewService.getReviewsByPetownerId(Long.parseLong(petownerId)); // 사용자 ID로 리뷰 조회
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token", "details", e.getMessage()));
        }
    }

//	// 리뷰 상세보기    
//    @GetMapping("/reviews/{reviewId}")
//    public ResponseEntity<?> getReviewDetail(@RequestHeader("Authorization") String token, @PathVariable Integer reviewId) {
//    	try {
//            jwtUtil.validateToken(token.replace("Bearer ", "")); // 토큰 검증
//            ReviewDTO review = myReviewService.getReviewById(reviewId); // 리뷰 조회
//            return ResponseEntity.ok(review);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token or unauthorized access"));
//        }
//    }

    
    // ======================================================================
    
    
    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(
            @RequestBody AddReviewRequest request,
            @RequestHeader("Authorization") String token) { // Authorization 헤더로부터 JWT 토큰 가져오기
        try {
            // 토큰에서 petownerId 추출
            String petownerId = jwtUtil.validateToken(token.replace("Bearer ", ""));
            Review createdReview = reviewService.addReview(request, Long.parseLong(petownerId), token);
            return ResponseEntity.ok(createdReview);
        } catch (Exception e) {
        	System.out.println("리뷰추가 에러 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to add review",
                "details", e.getMessage()
            ));
        }
    }


    // 병원 이름 존재 여부 확인 API
    @GetMapping("/addReview/check-hospital")
    public ResponseEntity<?> checkHospital(@RequestParam String hospitalName) {
    	boolean exists = hospitalFeignClient.checkHospitalExistsByName(hospitalName);
        if (exists) {
            return ResponseEntity.ok("해당 병원이 존재합니다.");
        } else {
            return ResponseEntity.ok("해당 병원이 존재하지 않습니다.");
        }
    }
}

