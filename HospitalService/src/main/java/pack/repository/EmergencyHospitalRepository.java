package pack.repository;

import pack.entity.EmergencyHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyHospitalRepository extends JpaRepository<EmergencyHospital, Long> {

    // 24시간 동물병원만 가져오기
    List<EmergencyHospital> findByIs24HourTrue();

    // 특정 지역 내 병원 찾기 (위도와 경도를 활용한 검색)
    List<EmergencyHospital> findByLatitudeBetweenAndLongitudeBetween(Double latStart, Double latEnd, Double lonStart, Double lonEnd);
}
