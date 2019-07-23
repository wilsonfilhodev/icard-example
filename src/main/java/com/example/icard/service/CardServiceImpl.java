package com.example.icard.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.icard.model.Card;
import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.model.dto.SuccessResponseDTO;
import com.example.icard.model.dto.TransationAuthorizationDTO;
import com.example.icard.repository.CardRepository;
import com.example.icard.service.execption.BusinessException;
import com.example.icard.utils.CreditCardUtils;
import com.example.icard.utils.DateValidtUtils;

@Service
public class CardServiceImpl implements CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Transactional
	public CardEmissionDTO save(CardEmissionDTO cardEmissionDTO) {
		cardEmissionDTO.setCardNumber(CreditCardUtils.generateNumberCreditCard());
		cardEmissionDTO.setExpirantionDate(DateValidtUtils.generate(LocalDate.now()));
		cardEmissionDTO.setCvv(CreditCardUtils.generateCvv(cardEmissionDTO.getCardNumber(), cardEmissionDTO.getExpirantionDate()));
		String password = CreditCardUtils.generatePassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		cardEmissionDTO.setPassword(passwordEncoder.encode(password));
		cardRepository.save(cardEmissionDTO.parseToCard());
		cardEmissionDTO.setPassword(password);
		return cardEmissionDTO;
	}

	public SuccessResponseDTO authorizeTransaction(TransationAuthorizationDTO transationAuthorizationDTO) {
		Optional<Card> optionalCard = cardRepository.findByCardNumber(transationAuthorizationDTO.getCardNumber());
		
		if(!optionalCard.isPresent()) throw new BusinessException("Transaction not authorized. Card invalid.", "101");
		
		Card card = optionalCard.get();
		
		validateIfCardNotExpirate(transationAuthorizationDTO);
		validateIfCvvIsValid(transationAuthorizationDTO);
		validatePassword(transationAuthorizationDTO, card);
		validateBalance(transationAuthorizationDTO, card);
		
		setNewBalance(transationAuthorizationDTO, card);
		
		return new SuccessResponseDTO("00", card.getBalance());
	}

	private void setNewBalance(TransationAuthorizationDTO transationAuthorizationDTO, Card card) {
		BigDecimal newBalance = card.getBalance().subtract(transationAuthorizationDTO.getSaleValue());
		card.setBalance(newBalance);
		cardRepository.save(card);
	}

	private void validateIfCardNotExpirate(TransationAuthorizationDTO transationAuthorizationDTO) {
		if(DateValidtUtils.cardExpired(transationAuthorizationDTO.getExpirantionDate())) throw new BusinessException("Transaction not authorized. Card expired.", "102");
	}
	
	private void validateIfCvvIsValid(TransationAuthorizationDTO transationAuthorizationDTO) {
		String cvvReceived = transationAuthorizationDTO.getCvv();
		String cvvGenerated = CreditCardUtils.generateCvv(transationAuthorizationDTO.getCardNumber(), transationAuthorizationDTO.getExpirantionDate());
		if(!cvvReceived.equals(cvvGenerated)) throw new BusinessException("Transaction not authorized. CVV invalid.", "103");
	}
	
	private void validatePassword(TransationAuthorizationDTO transationAuthorizationDTO, Card card) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Boolean passwordIsValid = passwordEncoder.matches(transationAuthorizationDTO.getPassword(), card.getPassword());
		if(!passwordIsValid) throw new BusinessException("Transaction not authorized. Password invalid.", "104"); 
	}
	
	private void validateBalance(TransationAuthorizationDTO transationAuthorizationDTO, Card card) {
		if(transationAuthorizationDTO.getSaleValue().compareTo(card.getBalance()) > 0)
			throw new BusinessException("Transaction not authorized. Balance insuficient.", "105"); 
	}
}
