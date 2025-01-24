package pack.service;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pack.entity.Hospital;
import pack.repository.HospitalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
 // 주소에 '서울특별시'가 포함된 병원만 가져오기

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findByAddressContaining("서울특별시");
    }//HospitalService는 비즈니스 로직을 처리하고, 데이터를 Repository를 통해 가져옵니다.
    

    // 특정 병원 ID로 병원 정보 가져오기
    public Optional<Hospital> getHospitalById(Long id) {
        return hospitalRepository.findById(id);
    }
}
//
//    @Autowired
//    private CacheManager cacheManager;
//    // 병원 데이터를 캐시하고 있다면
//    @Cacheable(value = "hospitals", key = "#id")
//    public Optional<Hospital> getHospitalById(Long id) {
//        return hospitalRepository.findById(id);
//    }
//
//    // 캐시 삭제 메서드 추가
//    public void clearHospitalCacheById(Long id) {
//        if (cacheManager.getCache("hospitals") != null) {
//            cacheManager.getCache("hospitals").evict(id);  // 캐시에서 해당 ID의 데이터를 삭제
//        }
//    }
//
//    // 모든 병원 캐시를 삭제하는 방법
//    public void clearAllHospitalCache() {
//        if (cacheManager.getCache("hospitals") != null) {
//            cacheManager.getCache("hospitals").clear();  // 모든 캐시 삭제
//        }
//    }

