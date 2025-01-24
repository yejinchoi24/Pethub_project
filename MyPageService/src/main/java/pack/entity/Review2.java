package pack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")  // 데이터베이스 테이블 이름 매핑
@Getter
@Setter
public class Review2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")  // 실제 DB의 컬럼 이름과 매핑
    private Integer reviewId;

    @Column(name = "petowner_id", nullable = false)  // 실제 DB의 컬럼 이름과 매핑
    private Integer petownerId;

    @Column(name = "hospital_id", nullable = false)  // 실제 DB의 컬럼 이름과 매핑
    private Long hospitalId;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "rating", nullable = false)  // int -> Integer로 매핑
    private Integer rating;

    @Column(name = "text", nullable = false, length = 255)  // varchar(255) -> String으로 매핑
    private String text;

    @Column(name = "visit_date", nullable = false, length = 255)  // varchar(255) -> String으로 매핑
    private String visitDate;

    @Column(name = "created_at", nullable = false, updatable = false)  // timestamp -> LocalDateTime으로 매핑
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)  // timestamp -> LocalDateTime으로 매핑
    private LocalDateTime updatedAt;
    
    @Column(name = "medical_item", length = 45)
    private String medicalItem;
}
