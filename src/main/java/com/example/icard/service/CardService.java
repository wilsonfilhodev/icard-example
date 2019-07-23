package com.example.icard.service;

import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.service.execption.CardNotFoundException;

public interface CardService {
	
	CardEmissionDTO save(CardEmissionDTO cardEmissionDTO);
	
	CardEmissionDTO findByNumber(String number) throws CardNotFoundException;
}
