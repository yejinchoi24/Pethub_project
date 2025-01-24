package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PetownerDTO {
	private Integer petownerId;
	private String name;
	private String phone;
    private String email;
    private String password;
}
