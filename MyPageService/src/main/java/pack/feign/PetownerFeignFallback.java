package pack.feign;

import org.springframework.stereotype.Component;

import pack.dto.PetownerDTO;

@Component
public class PetownerFeignFallback implements PetownerFeignClient {

	@Override
	public PetownerDTO getPetownerById(Long petownerId) {
	    System.err.println("Fallback: Unable to fetch Petowner for ID: " + petownerId);
	    return new PetownerDTO(); // 기본 객체 반환
	}

	@Override
	public PetownerDTO getPetownerById(Long petownerId, String token) {
	    System.err.println("Fallback: Unable to fetch Petowner for ID: " + petownerId + " with token: " + token);
	    return new PetownerDTO();
	}

	@Override
	public void update(PetownerDTO updatedPetownerDTO, String token) {
	    System.err.println("Fallback: Update failed for Petowner ID: " + updatedPetownerDTO.getPetownerId());
	}

}
