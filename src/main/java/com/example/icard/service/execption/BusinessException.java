package com.example.icard.service.execption;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3640304882498768559L;

	private final String code;

	public BusinessException(String code) {
		super();
		this.code = code;
	}

	public BusinessException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}

	public BusinessException(String message, String code) {
		super(message);
		this.code = code;
	}

	public BusinessException(Throwable cause, String code) {
		super(cause);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
