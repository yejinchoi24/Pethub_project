package pack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medical_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medical_category_id", nullable = false)
    private MedicalCategory medicalCategory;

    @Column(name = "name", nullable = false)
    private String name;
}

