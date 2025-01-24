package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pack.dto.PetownerDTO;
import pack.entity.Pet;
import pack.feign.PetownerFeignClient;
import pack.repository.PetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetownerFeignClient petownerFeignClient;

    // Pet 데이터를 저장
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    // 모든 Pet 데이터 조회
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // 특정 Pet 조회
    public Optional<Pet> getPetById(Long petId) {
        return petRepository.findById(petId);
    }

    // 특정 Petowner의 Pet 목록 조회
    public List<Pet> getPetsByPetownerId(Integer petownerId) {
        return petRepository.findByPetownerId(petownerId);
    }

    // Feign Client를 통해 Petowner 데이터 가져오기
    public PetownerDTO getPetownerDetails(Integer petownerId) {
        return petownerFeignClient.getPetownerById(petownerId);
    }
}
