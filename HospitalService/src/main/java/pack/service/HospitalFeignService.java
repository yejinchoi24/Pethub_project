package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.HospitalDTO;
import pack.entity.Hospital;
import pack.repository.HospitalFeignRepository;

@Service
public class HospitalFeignService {
	
	@Autowired
    HospitalFeignRepository hospitalFeignRepository;
	
	public HospitalDTO getHospitalByHospitalId(Long hospitalId) {
        Hospital hospital = hospitalFeignRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("병원 정보를 찾을 수 없습니다."));

        return new HospitalDTO(
                hospital.gethospitalId(),
                hospital.getHospitalName(),
                hospital.getAddress(),
                hospital.getPhoneNumber(),
                hospital.getOperatingHours(),
                hospital.getLat(),
                hospital.getLng(),
                hospital.getImagePath()
        );
    }
	
	public boolean existsByHospitalName(String hospitalName) {
        return hospitalFeignRepository.existsByHospitalName(hospitalName);
    }

}
