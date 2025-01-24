package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pack.entity.Like;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
	private Long likeId;
//	private Long petownerId;
	private Long hospitalId;
	private String hospitalName;
	private String image_Path;
	
	public static LikeDTO fromEntity(Like like, HospitalDTO hospitalDTO) {
        return new LikeDTO(
            like.getLikeId(),
            hospitalDTO.getHospitalId(),
            hospitalDTO.getHospitalName(),
            hospitalDTO.getImagePath()
        );
    }
}
