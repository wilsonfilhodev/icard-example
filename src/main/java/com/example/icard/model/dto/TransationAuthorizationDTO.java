package com.example.icard.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class TransationAuthorizationDTO {

	@NotNull(message = "Field 'cartao' is required")
	@JsonProperty("cartao")
	private String cardNumber;
	
	@NotNull(message = "Field 'validade' is required")
	@JsonProperty("validade")
	private String expirantionDate;
	
	@NotNull(message = "Field 'cvv' is required")
	@JsonProperty("cvv")
	private String cvv;
	
	@NotNull(message = "Field 'estabelecimento' is required")
	@JsonProperty("estabelecimento")
	private String client;
	
	@NotNull(message = "Field 'valor' is required")
	@JsonProperty("valor")
	private BigDecimal saleValue;
	
	@NotNull(message = "Field 'senha' is required")
	@JsonProperty("senha")
	private String password;

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
