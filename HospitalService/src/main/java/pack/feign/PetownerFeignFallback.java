package pack.feign;

import org.springframework.stereotype.Component;

import pack.dto.PetownerDTO;

@Component
public class PetownerFeignFallback implements PetownerFeignClient {

    @Override
    public PetownerDTO getPetownerById(Integer petownerId) {
        System.err.println("Fallback: Unable to fetch Petowner for ID: " + petownerId);
        return null; // 기본값 반환
    }

    @Override
    public PetownerDTO getPetownerById(Integer petownerId, String token) {
        System.err.println("Fallback: Unable to fetch Petowner for ID: " + petownerId + ". Token: " + token);
        return null; // 기본값 반환
    }

    @Override
    public void update(PetownerDTO updatedPetownerDTO, String token) {
        System.err.println("Fallback: Unable to update Petowner details. Token: " + token);
    }
}
