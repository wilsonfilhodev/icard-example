package com.example.icard.model.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiModel(description = "Class representing a transaction authorization.")
public class TransactionAuthorizationDTO {

	@JsonProperty("cartao")
	private String cardNumber;
	
	@JsonProperty("validade")
	private String expirantionDate;
	
	@JsonProperty("cvv")
	private String cvv;
	
	@JsonProperty("estabelecimento")
	private String client;
	
	@JsonProperty("valor")
	private BigDecimal saleValue;
	
	@JsonProperty("senha")
	private String password;

	public TransactionAuthorizationDTO() {
	}

	public TransactionAuthorizationDTO(String cardNumber, String expirantionDate, String cvv, String client,
			BigDecimal saleValue, String password) {
		this.cardNumber = cardNumber;
		this.expirantionDate = expirantionDate;
		this.cvv = cvv;
		this.client = client;
		this.saleValue = saleValue;
		this.password = password;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpirantionDate() {
		return expirantionDate;
	}

	public void setExpirantionDate(String expirantionDate) {
		this.expirantionDate = expirantionDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public BigDecimal getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(BigDecimal saleValue) {
		this.saleValue = saleValue;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
