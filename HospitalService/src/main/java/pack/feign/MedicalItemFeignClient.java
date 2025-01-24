package pack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pack.dto.MedicalItemDTO;

@FeignClient(
		name = "CHATTINGSERVICE", path = "/api/medicalItems",
		fallback = MedicalItemFeignFallback.class)
public interface MedicalItemFeignClient {

	@GetMapping("/{id}")
    MedicalItemDTO getMedicalItemById(@PathVariable Long id);
}
