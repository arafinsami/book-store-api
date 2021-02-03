package com.sami.dto;

import com.sami.entity.AppUser;

import lombok.Data;

@Data
public class ProfileDto {

	private Long id;
	
	private String firstName;

	private String lastName;

	private String username;

	private String email;

	private String password;

	public void update(AppUser user) {

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
	}

	public static ProfileDto from(AppUser user) {

		ProfileDto dto = new ProfileDto();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		return dto;
	}
}
