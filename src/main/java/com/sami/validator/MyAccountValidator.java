package com.sami.validator;

import static com.sami.utils.StringUtils.isNotEmpty;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sami.dto.SignupDto;
import com.sami.entity.AppUser;
import com.sami.exceptions.AppException;
import com.sami.service.AppUserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyAccountValidator implements Validator {

	private final AppUserService appUserService;

	@Override
	public boolean supports(Class<?> clazz) {
		return SignupDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SignupDto dto = (SignupDto) target;

		if (isNotEmpty(dto.getUsername())) {
			
			Optional<AppUser> optional = Optional.ofNullable(appUserService.findByUsername(dto.getUsername()));
			if (optional.isPresent()) {
				errors.reject("username", null, "username already exist");
			}
		}

		if (isNotEmpty(dto.getEmail())) {
			
			Optional<AppUser> optional = appUserService.findByEmail(dto.getEmail());
			
			if (optional.isPresent()) {
				errors.reject("email", null, "email already exist");
			}
		}
	}
}
