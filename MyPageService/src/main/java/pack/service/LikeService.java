package pack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pack.dto.HospitalDTO;
import pack.dto.LikeDTO;
import pack.entity.Like;
import pack.feign.HospitalFeignClient;
import pack.repository.LikeRepository;

@Service
@RequiredArgsConstructor
public class LikeService {
	
	@Autowired
	LikeRepository likeRepository;
    
	@Autowired
    private final HospitalFeignClient hospitalFeignClient;

    public List<LikeDTO> getLikesByPetownerId(Long petownerId) {
    	List<Like> likes = likeRepository.findByPetownerId(petownerId);

        return likes.stream()
                .map(like -> {
                    // FeignClient를 사용해 Hospital 정보 가져오기
                    HospitalDTO hospitalDTO = hospitalFeignClient.getHospitalById(like.getHospitalId());
                    return new LikeDTO(
                        like.getLikeId(),
                        hospitalDTO.getHospitalId(),
                        hospitalDTO.getHospitalName(),
                        hospitalDTO.getImagePath()
                    );
                })
                .collect(Collectors.toList());
    }
    
    
	// [ 병원 상세페이지 >> 병원 찜하기 ]
 // 찜 상태 확인
    public boolean isLiked(Long petownerId, Long hospitalId) {
        return likeRepository.existsByPetownerIdAndHospitalId(petownerId, hospitalId);
    }

    // 찜 상태 토글
    @Transactional // 찜 삭제하기 위해
    public boolean toggleLike(Long petownerId, Long hospitalId) {
        if (likeRepository.existsByPetownerIdAndHospitalId(petownerId, hospitalId)) {
            // 이미 존재하면 삭제
            likeRepository.deleteByPetownerIdAndHospitalId(petownerId, hospitalId);
            return false; // 찜 해제 상태 반환
        } else {
            // 존재하지 않으면 추가
            Like like = Like.builder()
                            .petownerId(petownerId)
                            .hospitalId(hospitalId)
                            .build();
            likeRepository.save(like);
            return true; // 찜 상태 반환
        }
    }

}
