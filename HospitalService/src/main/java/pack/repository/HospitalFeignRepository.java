package pack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.Hospital;

@Repository
public interface HospitalFeignRepository extends JpaRepository<Hospital, Long> {
    
    boolean existsByHospitalName(String hospitalName);
    
    Optional<Hospital> findByHospitalName(String hospitalName);
}
