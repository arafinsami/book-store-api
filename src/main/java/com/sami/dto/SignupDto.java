package com.sami.dto;

import com.sami.entity.AppUser;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupDto {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

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
