package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.entity.Pet;
import pack.service.PetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    // 1. Pet 데이터 저장 (Create)
    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        Pet savedPet = petService.savePet(pet);
        return ResponseEntity.ok(savedPet);
    }

    // 2. 모든 Pet 데이터 조회
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    // 3. 특정 Pet 데이터 조회
    @GetMapping("/{petId}")
    public ResponseEntity<?> getPetById(@PathVariable Long petId) {
        Optional<Pet> pet = petService.getPetById(petId);
        return pet.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. 특정 Petowner의 Pet 목록 조회
    @GetMapping("/owner/{petownerId}")
    public ResponseEntity<List<Pet>> getPetsByPetownerId(@PathVariable Integer petownerId) {
        List<Pet> pets = petService.getPetsByPetownerId(petownerId);
        return ResponseEntity.ok(pets);
    }

    // 5. Feign을 통해 Petowner 데이터 조회
    @GetMapping("/owner/details/{petownerId}")
    public ResponseEntity<?> getPetownerDetails(@PathVariable Integer petownerId) {
        return ResponseEntity.ok(petService.getPetownerDetails(petownerId));
    }
}
