package pack.service;

import org.springframework.stereotype.Service;
import pack.entity.Review2;
import pack.repository.ReviewRepository2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewService2 {

    private final ReviewRepository2 reviewRepository;

    public ReviewService2(ReviewRepository2 reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // 병원 순위 가져오기
    public List<Map<String, Object>> getHospitalRankings() {
        List<Object[]> results = reviewRepository.findHospitalRatingsWithAverage();

        return results.stream()
                .map(record -> Map.of(
                        "hospitalId", record[0],
                        "averageRating", record[1]
                ))
                .collect(Collectors.toList());
    }

    // 모든 리뷰 데이터 가져오기
    public List<Review2> getAllReviews() {
        return (List<Review2>) reviewRepository.findAll();
    }
}
