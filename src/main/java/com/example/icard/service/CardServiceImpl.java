package com.example.icard.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.icard.model.Card;
import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.repository.CardRepository;
import com.example.icard.service.execption.CardNotFoundException;
import com.example.icard.utils.CreditCardUtils;
import com.example.icard.utils.DateValidtUtils;

@Service
public class CardServiceImpl implements CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Transactional
	public CardEmissionDTO save(CardEmissionDTO cardEmissionDTO) {
		cardEmissionDTO.setNumber(CreditCardUtils.generateNumberCreditCard());
		cardEmissionDTO.setDateValidt(DateValidtUtils.generate(LocalDate.now()));
		cardEmissionDTO.setCvv(CreditCardUtils.generateCvv(cardEmissionDTO.getNumber(), cardEmissionDTO.getDateValidt()));
		String password = CreditCardUtils.generatePassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		cardEmissionDTO.setPassword(passwordEncoder.encode(password));
		cardRepository.save(cardEmissionDTO.parseToCard());
		cardEmissionDTO.setPassword(password);
		return cardEmissionDTO;
	}

	@Override
	public CardEmissionDTO findByNumber(String number) throws CardNotFoundException {
		Optional<Card> cardOptional = cardRepository.findByNumber(number);
		
		if(!cardOptional.isPresent()) {
			throw new CardNotFoundException("Card with number " + number + " not found");
		}
		
		return new CardEmissionDTO(cardOptional.get());
		
	}
	
}
