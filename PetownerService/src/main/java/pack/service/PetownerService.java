package pack.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pack.dto.PetownerDTO;
import pack.entity.Petowner;
import pack.repository.PetownerRepository;

@Service
@RequiredArgsConstructor
public class PetownerService {
	
	private final PetownerRepository petownerRepository;
	
	public void signup(PetownerDTO petownerDTO) {
		Petowner petowner = Petowner.toPetownerEntity(petownerDTO);
		petownerRepository.save(petowner);
	}
	
	public String emailCheck(String petownerEmail) {
        Optional<Petowner> byEmail = petownerRepository.findByEmail(petownerEmail);
        if (byEmail.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }
	
	// ------------------
	
	public PetownerDTO findById(Long petownerId) {
		Optional<Petowner> optionalPetowner = petownerRepository.findById(petownerId);
		if (optionalPetowner.isPresent()) {
			return PetownerDTO.toPetownerDTO(optionalPetowner.get());
		}
		else {
			return null;
		}
	}
	
//	// 추가
//	public PetownerDTO findByEmail(String petownerEmail) {
//		Optional<Petowner> byEmail = petownerRepository.findByEmail(petownerEmail);
//		if (byEmail.isPresent()) {
//			return PetownerDTO.toPetownerDTO(byEmail.get());
//		}
//		else {
//			return null;
//		}
//	}
	public PetownerDTO findByEmail(String email) {
        return petownerRepository.findByEmail(email)
            .map(PetownerDTO::toPetownerDTO)
            .orElse(null);
    }
	
	// ------------------
	
	public PetownerDTO login(PetownerDTO petownerDTO) {
	    Optional<Petowner> byEmail = petownerRepository.findByEmail(petownerDTO.getEmail());
	    if (byEmail.isPresent()) {
	        Petowner petowner = byEmail.get();
	        if (petowner.getPassword().equals(petownerDTO.getPassword())) {
	            System.out.println("로그인 성공: " + petowner.getEmail());
	            return PetownerDTO.toPetownerDTO(petowner);
	        } else {
	            System.out.println("로그인 실패: 비밀번호 불일치");
	        }
	    } else {
	        System.out.println("로그인 실패: 이메일 없음");
	    }
	    return null;
	}
	
	// ------------
	
	public PetownerDTO updateForm(String petownerEmail) {
        Optional<Petowner> optionalPetowner = petownerRepository.findByEmail(petownerEmail);
        if (optionalPetowner.isPresent()) {
            return PetownerDTO.toPetownerDTO(optionalPetowner.get());
        } else {
            return null;
        }
    }

	public void update(PetownerDTO petownerDTO) {
	    // 기존 데이터를 조회
	    Optional<Petowner> optionalPetowner = petownerRepository.findById(petownerDTO.getPetownerId());
	    
	    if (optionalPetowner.isPresent()) {
	        // 기존 데이터가 존재하는 경우에만 업데이트 수행
	        Petowner existingPetowner = optionalPetowner.get();
	        existingPetowner.setPassword(petownerDTO.getPassword());
	        existingPetowner.setName(petownerDTO.getName());
	        existingPetowner.setPhone(petownerDTO.getPhone());
	        // 필요한 필드 업데이트

	        petownerRepository.save(existingPetowner); // 기존 엔티티를 저장하여 업데이트
	    } else {
	        throw new RuntimeException("해당 사용자가 존재하지 않습니다.");
	    }
	}

}