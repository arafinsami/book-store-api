package com.sami.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ApiError {
	
	private static Map<String, String> errorMap = new HashMap<String, String>();

	public static Map<String, String> error(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				FieldError fieldError = (FieldError) objectError;
				errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
		return errorMap;
	}

}