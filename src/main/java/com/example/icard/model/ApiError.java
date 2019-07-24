package com.example.icard.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ApiError {

	@JsonProperty("codigo")
	private String code;
	
	private Date timestamp;
	
	@JsonProperty("mensagem")
	private String message;
	
	@JsonProperty("erros")
	private List<String> errors;

	public ApiError(String code, String message, List<String> errors) {
		this.code = code;
		this.timestamp = new Date();
		this.message = message;
		this.errors = errors;
	}

	public ApiError(String code, String message, String error) {
		this.code = code;
		this.timestamp = new Date();
		this.message = message;
		this.errors = Arrays.asList(error);
	}

	public ApiError(String code, String error) {
		this.code = code;
		this.errors = Arrays.asList(error);
	}

	public ApiError(String code, List<String> errors) {
		this.code = code;
		this.errors = errors;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
