package com.sami.dto;

import static com.sami.utils.Constants.CHARACTER_LIMIT;
import static com.sami.utils.Constants.REQUIRED;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sami.entity.AppUser;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupDto {

	@NotBlank(message = REQUIRED)
	@Size(max = 50, message = CHARACTER_LIMIT)
	private String username;

	@NotBlank(message = REQUIRED)
	@Size(max = 50, message = CHARACTER_LIMIT)
	private String password;

	@NotBlank(message = REQUIRED)
	@Size(max = 50, message = CHARACTER_LIMIT)
	private String firstName;

	@NotBlank(message = REQUIRED)
	@Size(max = 50, message = CHARACTER_LIMIT)
	private String lastName;

	@NotBlank(message = REQUIRED)
	@Size(max = 50, message = CHARACTER_LIMIT)
	private String email;

	public AppUser to() {

		AppUser user = new AppUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		return user;
	}

	public static SignupDto from(AppUser user) {

		SignupDto dto = new SignupDto();
		dto.setUsername(user.getUsername());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		return dto;
	}

}
