package pack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hospital_medical_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalMedicalItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_medical_item_id")
    private Long id;
	
	@Column(name = "hospital_id", nullable = false)
    private Long hospitalId;
	
	@Column(name = "medical_item_id", nullable = false)
    private Long medicalItemId;
	
}
