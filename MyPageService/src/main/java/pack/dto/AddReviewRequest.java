package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddReviewRequest {
//	private Long petownerId;
	private Long hospitalId;
	private Integer price;
	private String hospitalName;
	private String hospitalAddress;
	private String visitDate;
	private String medicalItem;
	private Integer rating;
	private String text;
}