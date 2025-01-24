package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.entity.Vaccination;
import pack.service.VaccinationService;

import java.util.List;

@RestController
@RequestMapping("/api/vaccination")
public class VaccinationController {
    @Autowired
    private VaccinationService vaccinationService;

    // 모든 Vaccination 조회
    @GetMapping
    public ResponseEntity<List<Vaccination>> getAllVaccinations() {
        List<Vaccination> vaccinations = vaccinationService.getAllVaccinations();
        return ResponseEntity.ok(vaccinations);
    }

    // ID로 Vaccination 조회
    @GetMapping("/{id}")
    public ResponseEntity<Vaccination> getVaccinationById(@PathVariable Long id) {
        Vaccination vaccination = vaccinationService.getVaccinationById(id);
        if (vaccination != null) {
            return ResponseEntity.ok(vaccination);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Vaccination 생성
    @PostMapping
    public ResponseEntity<Vaccination> createVaccination(@RequestBody Vaccination vaccination) {
        Vaccination savedVaccination = vaccinationService.saveVaccination(vaccination);
        return ResponseEntity.ok(savedVaccination);
    }

    // Vaccination 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccination(@PathVariable Long id) {
        vaccinationService.deleteVaccination(id);
        return ResponseEntity.noContent().build();
    }
}
