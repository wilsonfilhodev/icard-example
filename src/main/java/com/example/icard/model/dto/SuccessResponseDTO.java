package com.example.icard.model.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Class representing a success response for process of transaction authorization.")
public class SuccessResponseDTO {

	@JsonProperty("codigo")
	private String code;

	@JsonProperty("saldo")
	private BigDecimal balance;

	public SuccessResponseDTO(String code, BigDecimal balance) {
		this.code = code;
		this.balance = balance;
	}

	public SuccessResponseDTO() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
