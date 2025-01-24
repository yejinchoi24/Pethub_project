package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pack.entity.Vaccination;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    // 필요한 경우, 커스텀 쿼리를 추가할 수 있습니다.
}
