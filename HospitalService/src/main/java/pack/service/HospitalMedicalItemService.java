package pack.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.HospitalMedicalItemDTO;
import pack.dto.MedicalItemDTO;
import pack.entity.HospitalMedicalItem;
import pack.feign.MedicalItemFeignClient;
import pack.repository.HospitalMedicalItemRepository;

@Service
public class HospitalMedicalItemService {

	@Autowired
	HospitalMedicalItemRepository hospitalMedicalItemRepository;
	
	@Autowired
    private MedicalItemFeignClient medicalItemFeignClient;

	// 모든 병원의 진료 항목
    public List<HospitalMedicalItemDTO> getAllHospitalMedicalItems() {
        List<HospitalMedicalItem> entities = hospitalMedicalItemRepository.findAll();

        // 병원별로 진료 항목 이름 리스트 그룹화
        Map<Long, List<String>> groupedByHospitalId = entities.stream()
                .collect(Collectors.groupingBy(
                    HospitalMedicalItem::getHospitalId, // 병원 ID 기준으로 그룹화
                    Collectors.mapping(
                        entity -> {
                            // FeignClient를 사용해 medicalItemId로 이름 가져오기
                            MedicalItemDTO medicalItem = medicalItemFeignClient.getMedicalItemById(entity.getMedicalItemId());
                            return medicalItem.getName();
                        },
                        Collectors.toList()
                    )
                ));
        // DTO 리스트로 변환
        return groupedByHospitalId.entrySet().stream()
                .map(entry -> new HospitalMedicalItemDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

		
    // 특정 병원의 모든 진료 항목 이름 가져오기
    public List<String> getMedicalItemNamesByHospitalId(Long hospitalId) {
        List<HospitalMedicalItem> hospitalMedicalItems = hospitalMedicalItemRepository.findByHospitalId(hospitalId);

        // medicalItemId로 Chatting 마이크로서비스에서 이름 가져오기
        return hospitalMedicalItems.stream()
                .map(item -> medicalItemFeignClient.getMedicalItemById(item.getMedicalItemId()).getName())
                .collect(Collectors.toList());
    }
}
