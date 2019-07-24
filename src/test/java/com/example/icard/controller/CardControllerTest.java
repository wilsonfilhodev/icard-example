package com.example.icard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.icard.handler.ControllerExceptionHandler;
import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.model.dto.SuccessResponseDTO;
import com.example.icard.model.dto.TransactionAuthorizationDTO;
import com.example.icard.service.CardService;
import com.example.icard.service.execption.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class CardControllerTest {

	Logger logger = LoggerFactory.getLogger(CardControllerTest.class);
	
	private MockMvc mvc;

	@Mock
	private CardService cardService;

	@InjectMocks
	private CardController cardController;

	private JacksonTester<CardEmissionDTO> jsonCardEmissionDTO;
	
	private JacksonTester<TransactionAuthorizationDTO> jsonTransactionAuthorizationDTO;
	
	private JacksonTester<SuccessResponseDTO> jsonSuccessResponseDTO;
	
	private CardEmissionDTO cardEmissionDTOMock;
	
	private TransactionAuthorizationDTO transactionAuthorizationDTOMock;

	@Before
	public void setup() {
		logger.info("SETUP TEST");
		
		cardEmissionDTOMock = new CardEmissionDTO("Full Name", new BigDecimal("1000"));
		transactionAuthorizationDTOMock = Mockito.mock(TransactionAuthorizationDTO.class);
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(cardController).setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	public void givenCard_whenCallCreate_thenReturnJsonCard() throws Exception {
		logger.info("TEST CREATE CARD WITH SUCCESS");
		
		String jsonString = jsonCardEmissionDTO.write(cardEmissionDTOMock).getJson();

		given(cardService.save(Mockito.any(CardEmissionDTO.class))).willReturn(cardEmissionDTOMock);

		MockHttpServletResponse response = mvc
				.perform(post("/api/cards").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonString);
	}
	
	@Test
	public void givenExcpetion_whenCallCreate_thenReturnBadRequest() throws Exception {
		logger.info("TEST SAVE CARD WITH EXCEPTION OCURRED IN SERVICE");
		
		String jsonString = jsonCardEmissionDTO.write(cardEmissionDTOMock).getJson();
		
		given(cardService.save(Mockito.any(CardEmissionDTO.class))).willThrow(BusinessException.class);

		MockHttpServletResponse response = mvc
				.perform(post("/api/cards").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void givenAuthorizationTransactionDTO_whenCallAuthorizeTransaction_thenReturnJsonSuccess() throws Exception {
		logger.info("TEST AUTHORIZE TRANSACTION WITH SUCCESS");
		
		String jsonString = jsonTransactionAuthorizationDTO.write(transactionAuthorizationDTOMock).getJson();
		SuccessResponseDTO responseDTO = new SuccessResponseDTO("00", new BigDecimal("1000"));

		given(cardService.authorizeTransaction(Mockito.any(TransactionAuthorizationDTO.class))).willReturn(responseDTO);

		MockHttpServletResponse response = mvc
				.perform(post("/api/cards/authorization").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonSuccessResponseDTO.write(responseDTO).getJson());
	}

}
