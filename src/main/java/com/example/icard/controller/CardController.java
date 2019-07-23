package com.example.icard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.model.dto.SuccessResponseDTO;
import com.example.icard.model.dto.TransactionAuthorizationDTO;
import com.example.icard.service.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	@PostMapping
	public ResponseEntity<CardEmissionDTO> create(@RequestBody CardEmissionDTO cardEmissionDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(cardEmissionDTO));
	}
	
	@PostMapping("/authorization")
	public ResponseEntity<SuccessResponseDTO> authorizeTransaction(@RequestBody TransactionAuthorizationDTO transationAuthorizationDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(cardService.authorizeTransaction(transationAuthorizationDTO));
	}
}
