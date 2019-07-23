package com.example.icard.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.example.icard.model.Card;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class CardEmissionDTO {

	@NotNull(message = "Field 'nome' is required")
	@JsonProperty("nome")
	private String name;

	@JsonProperty("numero")
	private String number;

	private String cvv;

	@JsonProperty("validade")
	private String dateValidt;

	@JsonProperty("senha")
	private String password;

	@NotNull(message = "Field 'saldo' is required")
	@JsonProperty("saldo")
	private BigDecimal balance;
	
	
	public CardEmissionDTO() {
	}

	public CardEmissionDTO(Card card) {
		this.name = card.getName();
		this.number = card.getNumber();
		this.dateValidt = card.getDateValidt();
		this.password = card.getPassword();
		this.balance = card.getBalance();
	}
	
	public Card parseToCard() {
		Card card = new Card();
		card.setName(this.name);
		card.setNumber(this.number);
		card.setDateValidt(this.dateValidt);
		card.setPassword(this.password);
		card.setBalance(this.balance);
		
		return card;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getDateValidt() {
		return dateValidt;
	}

	public void setDateValidt(String dateValidt) {
		this.dateValidt = dateValidt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
