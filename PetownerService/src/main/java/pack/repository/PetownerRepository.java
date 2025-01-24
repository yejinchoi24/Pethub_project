package pack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.Petowner;

@Repository
public interface PetownerRepository extends JpaRepository<Petowner, Long> {
	Optional<Petowner> findByEmail(String email);
	Boolean existsByEmail(String email);
}