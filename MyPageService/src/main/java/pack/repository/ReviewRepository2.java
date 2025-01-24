package pack.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pack.entity.Review2;
import java.util.List;

@Repository
public interface ReviewRepository2 extends CrudRepository<Review2, Integer> {

    // 병원별 평균 평점을 계산하는 쿼리
    @Query("SELECT r.hospitalId, AVG(r.rating) AS avgRating " +
           "FROM Review2 r " +  // 엔티티 이름을 Review2로 수정
           "GROUP BY r.hospitalId " +
           "ORDER BY avgRating DESC")
    List<Object[]> findHospitalRatingsWithAverage();

    // 모든 리뷰 데이터를 반환
    Iterable<Review2> findAll();
}
