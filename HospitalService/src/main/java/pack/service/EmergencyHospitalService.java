package pack.service;

import pack.entity.EmergencyHospital;
import pack.repository.EmergencyHospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyHospitalService {

    private final EmergencyHospitalRepository repository;

    public EmergencyHospitalService(EmergencyHospitalRepository repository) {
        this.repository = repository;
    }

    // 모든 병원 가져오기
    public List<EmergencyHospital> getAllHospitals() {
        return repository.findAll();
    }

    // 24시간 병원만 가져오기
    public List<EmergencyHospital> get24HourHospitals() {
        return repository.findByIs24HourTrue();
    }

    // 특정 범위 내 병원 검색
    public List<EmergencyHospital> getHospitalsInRegion(Double latStart, Double latEnd, Double lonStart, Double lonEnd) {
        return repository.findByLatitudeBetweenAndLongitudeBetween(latStart, latEnd, lonStart, lonEnd);
    }
}
