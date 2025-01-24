package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.Hospital;

@Repository//HospitalRepository는 데이터베이스에서 데이터를 가져오는 SQL 쿼리의 역할을 합니다.
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    // 주소에 '서울특별시'가 포함된 병원 조회
    List<Hospital> findByAddressContaining(String keyword);
}
//1.findByAddressContaining:
//Spring Data JPA의 메서드 네이밍 규칙에 따라 Containing 키워드를 사용하여 특정 단어를 포함하는 데이터를 조회합니다.

//2.Spring Data JPA는 기본적으로 데이터를 저장, 조회, 수정, 삭제할 수 있는 메서드를 자동으로 제공합니다.