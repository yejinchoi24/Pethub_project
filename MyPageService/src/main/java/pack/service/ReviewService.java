package pack.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import pack.dto.AddReviewRequest;
import pack.dto.HospitalDTO;
import pack.dto.PetownerDTO;
import pack.dto.ReviewDTO;
import pack.entity.Review;
import pack.feign.HospitalFeignClient;
import pack.feign.PetownerFeignClient;
import pack.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
    
	@Autowired
	PetownerFeignClient petownerFeignClient;
	
	@Autowired
    private final HospitalFeignClient hospitalFeignClient;

	
	@CircuitBreaker(name = "petownerFeignClient", fallbackMethod = "fb_getPetownerById")
    @Retry(name = "petownerFeignClient")
    private PetownerDTO cb_getPetownerById(Long petownerId) {
        return petownerFeignClient.getPetownerById(petownerId);
    }
    @CircuitBreaker(name = "hospitalFeignClient", fallbackMethod = "fb_getHospitalById")
    @Retry(name = "hospitalFeignClient")
    private HospitalDTO cb_getHospitalById(Long hospitalId) {
        return hospitalFeignClient.getHospitalById(hospitalId);
    }

    // fallback
    private PetownerDTO fb_GetPetownerById(Long petownerId, Throwable throwable) {
        return new PetownerDTO();
    }
    private HospitalDTO fb_GetHospitalById(Long hospitalId, Throwable throwable) {
        return new HospitalDTO();
    }
    
	
    
    public List<ReviewDTO> getReviewsByPetownerId(Long petownerId) {
        List<Review> reviews = reviewRepository.findByPetownerId(petownerId);

//        PetownerDTO petownerDTO = petownerFeignClient.getPetownerById(petownerId);
        PetownerDTO petownerDTO = cb_getPetownerById(petownerId);

        return reviews.stream()
                .map(review -> {
                	HospitalDTO hospitalDTO = cb_getHospitalById(review.getHospitalId());
                    return ReviewDTO.fromEntity(review, petownerDTO, hospitalDTO);
                })
                .collect(Collectors.toList());
    }
    
    
    public Review addReview(AddReviewRequest request, Long petownerId, String token) {  
    	System.out.println("리뷰서비스 => 리뷰추가 request 병원Id : " + request.getHospitalId());
    	
    	PetownerDTO petownerDTO = petownerFeignClient.getPetownerById(petownerId, token);
        if (petownerDTO == null) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }
    	
        HospitalDTO hospitalDTO = hospitalFeignClient.checkHospitalByName(request.getHospitalName());
        if (hospitalDTO == null) {
            throw new IllegalArgumentException("병원 정보를 찾을 수 없습니다.");
        }
        System.out.println("리뷰서비스 => hospitalDTO 2 : " + hospitalDTO);
        
        Review review = new Review(
        		petownerId,
        		hospitalDTO.getHospitalId(),
                request.getPrice(),
                request.getRating(),
                request.getText(),
                request.getVisitDate(),
                request.getMedicalItem()
        );
        System.out.println("리뷰서비스 => review : " + review.getMedicalItem());
        return reviewRepository.save(review); // 데이터베이스에 저장
    }
    
    
}
