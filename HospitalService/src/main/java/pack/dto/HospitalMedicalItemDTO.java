package pack.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalMedicalItemDTO {

	private Long hospitalId;
    private List<String> medicalItemNames;
}
