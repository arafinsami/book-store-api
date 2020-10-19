package com.sami.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AppException() {
	}

	private String message;
	private HttpStatus httpStatus;

	public AppException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
