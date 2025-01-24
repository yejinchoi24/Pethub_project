package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.MedicalItemDTO;
import pack.entity.MedicalItem;
import pack.repository.MedicalItemRepository;

@Service
public class MedicalItemService {

	@Autowired
	private MedicalItemRepository medicalItemRepository;
	
	public MedicalItemDTO getMedicalItemById(Long id) {
        MedicalItem medicalItem = medicalItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MedicalItem not found with ID: " + id));
        
        // DTO로 변환 (생성자를 사용)
        return new MedicalItemDTO(medicalItem.getId(), medicalItem.getName());
    }
}
