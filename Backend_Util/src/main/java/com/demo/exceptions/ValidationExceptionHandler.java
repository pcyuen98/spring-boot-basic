
package com.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.utility_classes.ResponseEntityUtil;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ResponseEntity<Map<String, Object>> responseEntity = ResponseEntityUtil
				.getResponseEntity(ValidationExceptionHandler.class.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ex);

		if (logger.isErrorEnabled()) {
			logger.error("Exception handled: {}", responseEntity);
		}
		return responseEntity;

	}
}
