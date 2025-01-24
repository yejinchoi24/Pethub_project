package pack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hospital {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "operating_hours", length = 255)
    private String operatingHours;

    @Column(nullable = false, columnDefinition = "DOUBLE") // lat을 DOUBLE로 명시
    private Double lat;

    @Column(nullable = false, columnDefinition = "DOUBLE") // lng을 DOUBLE로 명시
    private Double lng;

    @Column(name = "image_path")
    private String imagePath;

    // Getter and Setter for id
    public Long gethospitalId() {
        return hospitalId;
    }

    public void setId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    // Getter and Setter for hospitalName
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for operatingHours
    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    // Getter and Setter for lat
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    // Getter and Setter for lng
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    // Getter and Setter for imagePath
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}