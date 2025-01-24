package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.HospitalMedicalItem;

@Repository
public interface HospitalMedicalItemRepository extends JpaRepository<HospitalMedicalItem, Long> {
	
	List<HospitalMedicalItem> findByHospitalId(Long hospitalId);
}
