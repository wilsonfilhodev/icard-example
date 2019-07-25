package com.example.icard.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.example.icard.utils.ExpirantionDatetUtils;

@Service
public class CardServiceImpl implements CardService {
	
	Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);
	
	private CardRepository cardRepository;
	
	public CardServiceImpl(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Transactional
	public CardEmissionDTO save(CardEmissionDTO cardEmissionDTO) {
		logger.info("PREPARING CARD FOR SAVE");
		
		verifyFieldsRequired(cardEmissionDTO);
		
		cardEmissionDTO.setCardNumber(CreditCardUtils.generateNumberCreditCard());
		cardEmissionDTO.setExpirantionDate(ExpirantionDatetUtils.generate(LocalDate.now()));
		cardEmissionDTO.setCvv(CreditCardUtils.generateCvv(cardEmissionDTO.getCardNumber(), cardEmissionDTO.getExpirantionDate()));
		String password = CreditCardUtils.generatePassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		cardEmissionDTO.setPassword(passwordEncoder.encode(password));
		
		cardRepository.save(cardEmissionDTO.parseToCard());
		
		logger.info("CARD SAVED");
		cardEmissionDTO.setPassword(password);
		return cardEmissionDTO;
	}

	public SuccessResponseDTO authorizeTransaction(TransactionAuthorizationDTO transactionAuthorizationDTO) {
		logger.info("INITIATING TRANSACTION AUTHORIZATION");
		
		verifyFieldsRequiredAuthorizationTransaction(transactionAuthorizationDTO);
		
		Optional<Card> optionalCard = cardRepository.findByCardNumber(transactionAuthorizationDTO.getCardNumber());
		
		if(!optionalCard.isPresent()) throw new BusinessException("Transação não autorizada. Cartão inválido.", "101");
		
		Card card = optionalCard.get();
		
		validateCardExpirationDate(transactionAuthorizationDTO, card);
		validateIfCvvIsValid(transactionAuthorizationDTO);
		validatePassword(transactionAuthorizationDTO, card);
		validateBalance(transactionAuthorizationDTO, card);
		
		setNewBalance(transactionAuthorizationDTO, card);
		
		logger.info("TRANSACTION AUTHORIZED");
		return new SuccessResponseDTO("00", card.getBalance());
	}

	private void verifyFieldsRequiredAuthorizationTransaction(TransactionAuthorizationDTO transactionAuthorizationDTO) {
		logger.info("VALIDATING REQUIRED FIELDS TO AUTHORIZE TRANSACTION");
		
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getCardNumber())) throw new BusinessException("Número do cartão é obrigatório", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getClient())) throw new BusinessException("Estabelecimento é obrigatório", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getCvv())) throw new BusinessException("Código CVV é obrigatório", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getExpirantionDate())) throw new BusinessException("Data de Validade é obrigatória", "400");
		if(!ExpirantionDatetUtils.isValidDate(transactionAuthorizationDTO.getExpirantionDate())) throw new BusinessException("Data de validade inválida", "400");
		if(StringUtils.isEmpty(transactionAuthorizationDTO.getPassword())) throw new BusinessException("Senha é obrigatória", "400");
		if(transactionAuthorizationDTO.getSaleValue() == null) throw new BusinessException("Valor da venda é obrigatória", "400");
	}

	private void setNewBalance(TransactionAuthorizationDTO transactionAuthorizationDTO, Card card) {
		BigDecimal newBalance = card.getBalance().subtract(transactionAuthorizationDTO.getSaleValue());
		card.setBalance(newBalance);
		cardRepository.save(card);
	}

	private void validateCardExpirationDate(TransactionAuthorizationDTO transactionAuthorizationDTO, Card card) {
		if(ExpirantionDatetUtils.cardExpired(transactionAuthorizationDTO.getExpirantionDate())) 
				throw new BusinessException("Transação não autorizada. Data de validade expirada.", "102");
		if(!transactionAuthorizationDTO.getExpirantionDate().equals(card.getExpirantionDate())) 
			throw new BusinessException("Transação não autorizada. Data de validade incorreta.", "106");
	}
	
	private void validateIfCvvIsValid(TransactionAuthorizationDTO transactionAuthorizationDTO) {
		String cvvReceived = transactionAuthorizationDTO.getCvv();
		String cvvGenerated = CreditCardUtils.generateCvv(transactionAuthorizationDTO.getCardNumber(), transactionAuthorizationDTO.getExpirantionDate());
		if(!cvvReceived.equals(cvvGenerated)) throw new BusinessException("Transação não autorizadad. Código CVV é invádlido.", "103");
	}
	
	private void validatePassword(TransactionAuthorizationDTO transactionAuthorizationDTO, Card card) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Boolean passwordIsValid = passwordEncoder.matches(transactionAuthorizationDTO.getPassword(), card.getPassword());
		if(!passwordIsValid) throw new BusinessException("Transação não autorizada. Senha incorreta.", "104"); 
	}
	
	private void validateBalance(TransactionAuthorizationDTO transactionAuthorizationDTO, Card card) {
		if(transactionAuthorizationDTO.getSaleValue().compareTo(card.getBalance()) > 0)
			throw new BusinessException("Transação não autorizada. Saldo insuficiente.", "105"); 
	}
	
	private void verifyFieldsRequired(CardEmissionDTO cardEmissionDTO) {
		logger.info("VALIDATING FIELDS REQUIRED FOR CREATE NEW CARD");
		
		if(StringUtils.isEmpty(cardEmissionDTO.getName())) throw new BusinessException("Nome é obrigatório", "400");
		if(StringUtils.isEmpty(cardEmissionDTO.getBalance())) throw new BusinessException("Saldo é obrigatório", "400");
	}
}
