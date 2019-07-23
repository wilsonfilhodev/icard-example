package com.example.icard.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.icard.model.ApiError;
import com.example.icard.service.execption.BusinessException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ BusinessException.class })
	public ResponseEntity<ApiError> handleRequiredFieldException(BusinessException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getCode(), e.getMessage()));
	}

}
