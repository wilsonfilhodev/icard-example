package com.example.icard.service;

import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.model.dto.SuccessResponseDTO;
import com.example.icard.model.dto.TransationAuthorizationDTO;

public interface CardService {
	
	CardEmissionDTO save(CardEmissionDTO cardEmissionDTO);
	
	SuccessResponseDTO authorizeTransaction(TransationAuthorizationDTO transationAuthorizationDTO);
}
