package pack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pack.entity.Review2;
import pack.service.ReviewService2;

import java.util.List;
import java.util.Map;

//모든 리뷰 가져오기: http://localhost:8765/api/reviews2
//병원 순위 가져오기: http://localhost:8765/api/reviews2/rankings
@RestController
@RequestMapping("/api/reviews2")
public class ReviewController2 {

    private final ReviewService2 reviewService;

    public ReviewController2(ReviewService2 reviewService) {
        this.reviewService = reviewService;
    }

    // 병원 순위 가져오기
    @GetMapping("/rankings")
    public List<Map<String, Object>> getHospitalRankings() {
        return reviewService.getHospitalRankings();
    }

    // 모든 리뷰 데이터 가져오기
    @GetMapping
    public List<Review2> getAllReviews() {
        return reviewService.getAllReviews();
    }
}
