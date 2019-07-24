package com.example.icard.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.icard.model.Card;
import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.model.dto.TransactionAuthorizationDTO;
import com.example.icard.repository.CardRepository;
import com.example.icard.service.execption.BusinessException;

@RunWith(SpringRunner.class)
public class CardServiceTest {

	Logger logger = LoggerFactory.getLogger(CardServiceTest.class);
	
	@MockBean
    private CardRepository cardRepository;
	
	private CardService cardService;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private TransactionAuthorizationDTO transactionAuthorizationDTOMokc;
	
	@Before
	public void setup() {
		logger.info("SETUP TEST");
		
		transactionAuthorizationDTOMokc = new TransactionAuthorizationDTO(
				"5555550434374908", "07/21", "301", "Empresa XYZ", new BigDecimal("1000"), "2684");
		cardService = new CardServiceImpl(cardRepository);
    }
	
	@Test
	public void givenCardWithoutName_whenSaveCard_thenReturnExcpetion() throws Exception {
		logger.info("TEST SAVE CARD WHITHOUT NAME");
		
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Nome é obrigatório");
		
		CardEmissionDTO dto = new CardEmissionDTO();
		dto.setName(null);
		dto.setBalance(BigDecimal.ONE);
		
		cardService.save(dto);
	}
	
	@Test
	public void testSaveCard() throws Exception {
		logger.info("TEST SAVE CARD WITH SUCCESS");
		
		CardEmissionDTO dto = new CardEmissionDTO();
		dto.setName("My Full Name");
		dto.setBalance(BigDecimal.TEN);
		Card card = dto.parseToCard();
		
		Mockito.when(cardRepository.save(card)).thenReturn(card);
		
		CardEmissionDTO cardSaved = cardService.save(dto);
		
		assertNotNull(cardSaved.getCardNumber());
		
	}
	
	@Test
	public void givenCardWithoutCardNumber_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		logger.info("TEST AUTHORIZE TRANSACTION WITHOUT CARD NUMBER");
		
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Número do cartão é obrigatório");
		
		transactionAuthorizationDTOMokc.setCardNumber(null);
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
	@Test
	public void givenCardWithValidateExpired_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		logger.info("TEST AUTHORIZE TRANSACTION WITHOUT CARD EXPIRED");
		
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Transação não autorizada. Data de validade expirada.");
		
		transactionAuthorizationDTOMokc.setExpirantionDate("07/18");
		
		Mockito.when(cardRepository.findByCardNumber(transactionAuthorizationDTOMokc.getCardNumber())).thenReturn(Optional.of(new Card()));
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
}
