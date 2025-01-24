package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pack.entity.Vaccination;
import pack.repository.VaccinationRepository;

import java.util.List;

@Service
public class VaccinationService {
    @Autowired
    private VaccinationRepository vaccinationRepository;

    // 모든 Vaccination 조회
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAll();
    }

    // ID로 Vaccination 조회
    public Vaccination getVaccinationById(Long id) {
        return vaccinationRepository.findById(id).orElse(null);
    }

    // Vaccination 생성 및 저장
    public Vaccination saveVaccination(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }

    // Vaccination 삭제
    public void deleteVaccination(Long id) {
        vaccinationRepository.deleteById(id);
    }
}
