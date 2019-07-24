package com.example.icard.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.icard.model.Card;
import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.model.dto.SuccessResponseDTO;
import com.example.icard.model.dto.TransactionAuthorizationDTO;
import com.example.icard.repository.CardRepository;
import com.example.icard.service.execption.BusinessException;
import com.example.icard.utils.CreditCardUtils;
import com.example.icard.utils.DateValidtUtils;

@Service
public class CardServiceImpl implements CardService {
	
	private CardRepository cardRepository;
	
	public CardServiceImpl(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Transactional
	public CardEmissionDTO save(CardEmissionDTO cardEmissionDTO) {
		verifyFieldsRequired(cardEmissionDTO);
		
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

	public SuccessResponseDTO authorizeTransaction(TransactionAuthorizationDTO transactionAuthorizationDTO) {
		verifyFieldsRequiredAuthorizationTransaction(transactionAuthorizationDTO);
		
		Optional<Card> optionalCard = cardRepository.findByCardNumber(transactionAuthorizationDTO.getCardNumber());
		
		if(!optionalCard.isPresent()) throw new BusinessException("Transaction not authorized. Card invalid.", "101");
		
		Card card = optionalCard.get();
		
		validateIfCardNotExpirate(transactionAuthorizationDTO);
		validateIfCvvIsValid(transactionAuthorizationDTO);
		validatePassword(transactionAuthorizationDTO, card);
		validateBalance(transactionAuthorizationDTO, card);
		
		setNewBalance(transactionAuthorizationDTO, card);
		
		return new SuccessResponseDTO("00", card.getBalance());
	}

	private void verifyFieldsRequiredAuthorizationTransaction(TransactionAuthorizationDTO transactionAuthorizationDTO) {
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getCardNumber())) throw new BusinessException("Field 'cartao' is required", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getClient())) throw new BusinessException("Field 'estabelecimento' is required", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getCvv())) throw new BusinessException("Field 'CVV' is required", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getExpirantionDate())) throw new BusinessException("Field 'validade' is required", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getPassword())) throw new BusinessException("Field 'senha' is required", "400");
		if(transactionAuthorizationDTO.getSaleValue() == null) throw new BusinessException("Field 'valor' is required", "400");
	}

	private void setNewBalance(TransactionAuthorizationDTO transactionAuthorizationDTO, Card card) {
		BigDecimal newBalance = card.getBalance().subtract(transactionAuthorizationDTO.getSaleValue());
		card.setBalance(newBalance);
		cardRepository.save(card);
	}

	private void validateIfCardNotExpirate(TransactionAuthorizationDTO transactionAuthorizationDTO) {
		if(DateValidtUtils.cardExpired(transactionAuthorizationDTO.getExpirantionDate())) throw new BusinessException("Transaction not authorized. Card expired.", "102");
	}
	
	private void validateIfCvvIsValid(TransactionAuthorizationDTO transactionAuthorizationDTO) {
		String cvvReceived = transactionAuthorizationDTO.getCvv();
		String cvvGenerated = CreditCardUtils.generateCvv(transactionAuthorizationDTO.getCardNumber(), transactionAuthorizationDTO.getExpirantionDate());
		if(!cvvReceived.equals(cvvGenerated)) throw new BusinessException("Transaction not authorized. CVV invalid.", "103");
	}
	
	private void validatePassword(TransactionAuthorizationDTO transactionAuthorizationDTO, Card card) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Boolean passwordIsValid = passwordEncoder.matches(transactionAuthorizationDTO.getPassword(), card.getPassword());
		if(!passwordIsValid) throw new BusinessException("Transaction not authorized. Password invalid.", "104"); 
	}
	
	private void validateBalance(TransactionAuthorizationDTO transactionAuthorizationDTO, Card card) {
		if(transactionAuthorizationDTO.getSaleValue().compareTo(card.getBalance()) > 0)
			throw new BusinessException("Transaction not authorized. Balance insuficient.", "105"); 
	}
	
	private void verifyFieldsRequired(CardEmissionDTO cardEmissionDTO) {
		if(StringUtils.isEmpty(cardEmissionDTO.getName())) throw new BusinessException("Field 'nome' is required", "400");
		if(StringUtils.isEmpty(cardEmissionDTO.getBalance())) throw new BusinessException("Field 'saldo' is required", "400");
	}
}
