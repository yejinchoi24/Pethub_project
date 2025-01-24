package pack.controller;

import pack.entity.EmergencyHospital;
import pack.service.EmergencyHospitalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//모든 병원 조회:http://localhost:8765/hospitals/emergency-hospitals
//24시간 병원만 조회:http://localhost:8765/hospitals/emergency-hospitals/24-hour
//특정 범위 내 병원 조회 (예시: 위도와 경도 범위를 37.0 ~ 37.5, 127.0 ~ 127.5로 설정)http://localhost:8765/hospitals/emergency-hospitals/region?latStart=37

@RestController
@RequestMapping("/hospitals/emergency-hospitals")
public class EmergencyHospitalController {

    private final EmergencyHospitalService service;

    public EmergencyHospitalController(EmergencyHospitalService service) {
        this.service = service;
    }

    // 모든 병원 정보 반환
    @GetMapping
    public List<EmergencyHospital> getAllHospitals() {
        return service.getAllHospitals();
    }

    // 24시간 동물병원만 반환
    @GetMapping("/24-hour")
    public List<EmergencyHospital> get24HourHospitals() {
        return service.get24HourHospitals();
    }

    // 특정 범위 내 병원 정보 반환
    @GetMapping("/region")
    public List<EmergencyHospital> getHospitalsInRegion(Double latStart, Double latEnd, Double lonStart, Double lonEnd) {
        return service.getHospitalsInRegion(latStart, latEnd, lonStart, lonEnd);
    }
}
