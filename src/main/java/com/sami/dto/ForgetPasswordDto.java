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
public class ForgetPasswordDto {

	@NotBlank(message = REQUIRED)
	@Size(max = 50, message = CHARACTER_LIMIT)
	private String email;

	public static ForgetPasswordDto from(AppUser user) {

		ForgetPasswordDto dto = new ForgetPasswordDto();
		dto.setEmail(user.getEmail());
		return dto;
	}
}
