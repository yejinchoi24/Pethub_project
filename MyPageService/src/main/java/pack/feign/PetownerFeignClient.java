package pack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import pack.dto.PetownerDTO;

@FeignClient(
		name = "PETOWNERSERVICE", path = "/api/petowner",
		fallback = PetownerFeignFallback.class)
public interface PetownerFeignClient {
    
	@GetMapping("/{petownerId}")
    PetownerDTO getPetownerById(@PathVariable Long petownerId);
	
    @GetMapping("/{petownerId}")
    PetownerDTO getPetownerById(
        @PathVariable Long petownerId, 
        @RequestHeader("Authorization") String token
    );
    
    @PutMapping("/update")
    void update(
        @RequestBody PetownerDTO updatedPetownerDTO,
        @RequestHeader("Authorization") String token
    );
}
