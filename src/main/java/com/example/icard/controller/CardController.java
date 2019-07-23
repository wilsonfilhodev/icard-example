package com.example.icard.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.service.CardService;

@RestController("/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	
	@PostMapping
	public ResponseEntity<CardEmissionDTO> create(@Valid @RequestBody CardEmissionDTO cardEmissionDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(cardEmissionDTO));
	}
}
