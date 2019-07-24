package com.example.icard.model.dto;

import java.math.BigDecimal;

import com.example.icard.model.Card;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class CardEmissionDTO {

	@JsonProperty("nome")
	private String name;

	@JsonProperty("numero")
	private String cardNumber;

	private String cvv;

	@JsonProperty("validade")
	private String expirantionDate;

	@JsonProperty("senha")
	private String password;

	@JsonProperty("saldo")
	private BigDecimal balance;

	public CardEmissionDTO() {
	}

	public CardEmissionDTO(Card card) {
		this.name = card.getName();
		this.cardNumber = card.getCardNumber();
		this.expirantionDate = card.getExpirantionDate();
		this.password = card.getPassword();
		this.balance = card.getBalance();
	}

	public CardEmissionDTO(String name, BigDecimal balance) {
		this.name = name;
		this.balance = balance;
	}

	public Card parseToCard() {
		Card card = new Card();
		card.setName(this.name);
		card.setCardNumber(this.cardNumber);
		card.setExpirantionDate(this.expirantionDate);
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getExpirantionDate() {
		return expirantionDate;
	}

	public void setExpirantionDate(String expirantionDate) {
		this.expirantionDate = expirantionDate;
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
