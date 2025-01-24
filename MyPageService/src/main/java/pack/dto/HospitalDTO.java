package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {
    private Long hospitalId;
    private String hospitalName;
    private String address;
    private String phoneNumber;
    private String operatingHours;
    private Double lat;
    private Double lng;
    private String imagePath;
}
