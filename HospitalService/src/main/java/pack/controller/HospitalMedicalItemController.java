package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.HospitalMedicalItemDTO;
import pack.service.HospitalMedicalItemService;

@RestController
@RequestMapping("/api/hospital-medicalItems")
public class HospitalMedicalItemController {
	
	@Autowired
	HospitalMedicalItemService hospitalMedicalItemService;
	
	@GetMapping("")
	public ResponseEntity<List<HospitalMedicalItemDTO>> getAllHospitalMedicalItems() {
	    List<HospitalMedicalItemDTO> allMedicalItems = hospitalMedicalItemService.getAllHospitalMedicalItems();
	    return ResponseEntity.ok(allMedicalItems);
	}
	
	@GetMapping("/{hospitalId}")
    public ResponseEntity<List<String>> getMedicalItemNamesByHospitalId(@PathVariable Long hospitalId) {
        List<String> medicalItemNames = hospitalMedicalItemService.getMedicalItemNamesByHospitalId(hospitalId);
        return ResponseEntity.ok(medicalItemNames);
    }
}
