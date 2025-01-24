package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pack.entity.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByPetownerId(Integer petownerId); // 특정 Petowner의 Pet 목록 조회
}