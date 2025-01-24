package pack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", updatable = false)
    private Long reviewId;

    @Column(name = "petowner_id", nullable = false)
    private Long petownerId;
    
    @Column(name = "hospital_id", nullable = false)
    private Long hospitalId;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "text", nullable = false, length = 255)
    private String text;

    @Column(name = "visit_date", nullable = false, length = 255)
    private String visitDate;

    @Column(name = "medical_item", length = 45)
    private String medicalItem;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Review(Long petownerId, Long hospitalId, Integer price, Integer rating, 
    				String text, String visitDate, String medicalItem) {
        this.petownerId = petownerId;
        this.hospitalId = hospitalId;
        this.price = price;
        this.rating = rating;
        this.text = text;
        this.visitDate = visitDate;
        this.medicalItem = medicalItem;
    }
}
