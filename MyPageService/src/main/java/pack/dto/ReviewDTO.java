package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pack.entity.Review;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private String petownerName;
    private String hospitalName;
    private String hospitalAddress;
    private Integer price;
    private Integer rating;
    private String text;
    private String visitDate;
    private String medicalItem;
    private LocalDateTime createdAt;

    public static ReviewDTO fromEntity(Review review, PetownerDTO petownerDTO, HospitalDTO hospitalDTO) {
        return new ReviewDTO(
            review.getReviewId(),
            petownerDTO.getName(), // FeignClient로 가져온 PetownerDTO
            hospitalDTO.getHospitalName(), // HospitalFeignClient로 가져온 HospitalDTO
            hospitalDTO.getAddress(),
            review.getPrice(),
            review.getRating(),
            review.getText(),
            review.getVisitDate(),
            review.getMedicalItem(),
            review.getCreatedAt()
        );
    }
}
