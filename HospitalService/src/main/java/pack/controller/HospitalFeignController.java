package pack.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.HospitalDTO;
import pack.entity.Hospital;
import pack.repository.HospitalFeignRepository;
import pack.service.HospitalFeignService;

@RestController
@RequestMapping("/api/hospital")
public class HospitalFeignController {
	
	@Autowired
    private HospitalFeignService hospitalFeignService;   
	
	@Autowired
	private HospitalFeignRepository hospitalFeignRepository;

    @GetMapping("/{hospitalId}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long hospitalId) {
    	System.out.println("병원 컨트롤러 => hospitalId : " + hospitalId);
        HospitalDTO hospitalDTO = hospitalFeignService.getHospitalByHospitalId(hospitalId);
        return ResponseEntity.ok(hospitalDTO);
    }
    
    
    @GetMapping("/checkname")
    public ResponseEntity<?> checkHospitalByName(@RequestParam String hospitalName) {
        Optional<Hospital> hospital = hospitalFeignRepository.findByHospitalName(hospitalName);
        if (hospital.isPresent()) {
            HospitalDTO hospitalDTO = new HospitalDTO(
                hospital.get().gethospitalId(),
                hospital.get().getHospitalName(),
                hospital.get().getAddress(),
                hospital.get().getPhoneNumber(),
                hospital.get().getOperatingHours(),
                hospital.get().getLat(),
                hospital.get().getLng(),
                hospital.get().getImagePath()
            );
            return ResponseEntity.ok(hospitalDTO); // 병원 정보를 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 병원을 찾을 수 없습니다.");
        }
    }
    
    
    @GetMapping("/checkExists")
    public ResponseEntity<Boolean> checkHospitalExistsByName(@RequestParam String hospitalName) {
        boolean exists = hospitalFeignRepository.existsByHospitalName(hospitalName);
        return ResponseEntity.ok(exists);
    }

}
