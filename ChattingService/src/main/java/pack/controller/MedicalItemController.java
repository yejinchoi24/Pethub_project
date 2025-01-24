package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.MedicalItemDTO;
import pack.service.MedicalItemService;

@RestController
@RequestMapping("/api/medicalItems")
public class MedicalItemController {
	
	@Autowired
	private MedicalItemService medicalItemService;
	
	@GetMapping("/{id}")
	public ResponseEntity<MedicalItemDTO> getMedicalItemById(@PathVariable Long id) {
        MedicalItemDTO medicalItemDTO = medicalItemService.getMedicalItemById(id);
        return ResponseEntity.ok(medicalItemDTO);
    } 
}
