package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pack.entity.Hospital;
import pack.service.HospitalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;    
    
    // 모든 병원 목록 가져오기
    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }//hospitalService.getAllHospitals()를 호출하고 반환된 데이터를 클라이언트에 전달합니다.


    // 특정 병원 ID로 병원 정보 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
        Optional<Hospital> hospital = hospitalService.getHospitalById(id);
        return hospital.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
//SELECT * FROM hospital WHERE address LIKE '%서울특별시%';



//-----
//package pack.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pack.service.HospitalService;
//
//@RestController
//public class HospitalController {
//
//    @Autowired
//    private HospitalService hospitalService;
//
//    // 한 번만 캐시 삭제하는 API
//    @GetMapping("/clear-hospital-cache")
//    public String clearCache() {
//        hospitalService.clearAllHospitalCache();  // 캐시를 한 번만 삭제
//        return "Hospital cache cleared.";
//    }
//}


//------------
//package pack.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import pack.entity.Hospital;
//import pack.service.HospitalService;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/hospitals")
//public class HospitalController {
//
//    @Autowired
//    private HospitalService hospitalService;
//
//    // 모든 병원 목록 가져오기
//    @GetMapping
//    public List<Hospital> getAllHospitals() {
//        return hospitalService.getAllHospitals();
//    }
//
//    // 특정 병원 ID로 병원 정보 가져오기
//    @GetMapping("/{id}")
//    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
//        Optional<Hospital> hospital = hospitalService.getHospitalById(id);
//        return hospital.map(ResponseEntity::ok)
//                       .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//}