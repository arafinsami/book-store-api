package com.sami.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(AppException.class)
	public void handleCustomException(HttpServletResponse res, AppException e) throws IOException {
		LOG.error("ERROR", e);
		res.sendError(e.getHttpStatus().value(), e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(HttpServletResponse res, IllegalArgumentException e) throws IOException {
		LOG.error("ERROR", e);
		res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
	}

	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletResponse res, Exception e) throws IOException {
		LOG.error("ERROR", e);
		res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
	}
}
