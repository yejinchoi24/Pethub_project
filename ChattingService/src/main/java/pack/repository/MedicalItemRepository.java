package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.MedicalItem;

@Repository
public interface MedicalItemRepository extends JpaRepository<MedicalItem, Long> {
}