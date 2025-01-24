package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import pack.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
	
	// [ 마이페이지 >> 찜한 병원 ]
	List<Like> findByPetownerId(Long petownerId);
	
	// ---------------------------------------------------------------------------
	
	// [ 병원 상세페이지 >> 병원 찜하기 ]
	boolean existsByPetownerIdAndHospitalId(Long petownerId, Long hospitalId);
	
	@Transactional
	void deleteByPetownerIdAndHospitalId(Long petownerId, Long hospitalId);
}
