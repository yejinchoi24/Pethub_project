package pack.feign;

import org.springframework.stereotype.Component;

import pack.dto.HospitalDTO;

@Component
public class HospitalFeignFallback implements HospitalFeignClient {

    @Override
    public HospitalDTO getHospitalById(Long hospitalId) {
        System.err.println("Fallback: Unable to fetch hospital details for ID: " + hospitalId);
        return new HospitalDTO(); // 기본값 반환
    }

    @Override
    public HospitalDTO checkHospitalByName(String hospitalName) {
        System.err.println("Fallback: Unable to fetch hospital details for name: " + hospitalName);
        return null;
    }

    @Override
    public boolean checkHospitalExistsByName(String hospitalName) {
        System.err.println("Fallback: Unable to check hospital existence for name: " + hospitalName);
        return false; // 기본값 반환
    }
}