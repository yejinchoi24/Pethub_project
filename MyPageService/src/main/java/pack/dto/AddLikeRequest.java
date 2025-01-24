package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddLikeRequest {
	private Long hospitalId;
	private String hospitalName;
	private String imagePath;
}
