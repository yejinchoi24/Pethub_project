package pack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dto.HospitalDTO;

@FeignClient(
		name = "HOSPITALSERVICE", path = "/api/hospital",
		fallback = HospitalFeignFallback.class)
public interface HospitalFeignClient {

    @GetMapping("/{hospitalId}")
    HospitalDTO getHospitalById(@PathVariable Long hospitalId);
	
//	// [ CircuitBreaker Open 테스트용 ]
//	//		|-> http://localhost:8765/actuator/circuitbreakers
//	@GetMapping("/{hospitalId}")
//    default HospitalDTO getHospitalById(@PathVariable Long hospitalId) {
//        try {
//            // 5초 지연
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // 실패하도록 강제로 null 반환
//        return null;
//    }
    
    @GetMapping("/checkname")
    HospitalDTO checkHospitalByName(@RequestParam String hospitalName);
    
    @GetMapping("/checkExists")
    boolean checkHospitalExistsByName(@RequestParam String hospitalName);
}
