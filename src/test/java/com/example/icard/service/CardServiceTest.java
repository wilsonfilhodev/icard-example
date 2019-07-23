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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.icard.model.Card;
import com.example.icard.model.dto.CardEmissionDTO;
import com.example.icard.model.dto.TransactionAuthorizationDTO;
import com.example.icard.repository.CardRepository;
import com.example.icard.service.execption.BusinessException;

@RunWith(SpringRunner.class)
public class CardServiceTest {

	@MockBean
    private CardRepository cardRepository;
	
	private CardService cardService;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private TransactionAuthorizationDTO transactionAuthorizationDTOMokc;
	
	@Before
	public void setup() {
		transactionAuthorizationDTOMokc = new TransactionAuthorizationDTO(
				"5555551234567890", "12/22", "333", "Empresa XYZ", new BigDecimal("1000"), "1234");
		cardService = new CardServiceImpl(cardRepository);
    }
	
	@Test
	public void givenCardWithoutName_whenSaveCard_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'nome' is required");
		
		CardEmissionDTO dto = new CardEmissionDTO();
		dto.setName(null);
		dto.setBalance(BigDecimal.ONE);
		
		cardService.save(dto);
	}
	
	@Test
	public void givenCardWithoutBalance_whenSaveCard_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'saldo' is required");
		
		CardEmissionDTO dto = new CardEmissionDTO();
		dto.setName("My Full Name");
		dto.setBalance(null);
		
		cardService.save(dto);
	}
	
	@Test
	public void testSaveCard() throws Exception {
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
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'cartao' is required");
		
		transactionAuthorizationDTOMokc.setCardNumber(null);
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
	@Test
	public void givenCardWithoutClient_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'estabelecimento' is required");
		
		transactionAuthorizationDTOMokc.setClient(null);
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
	@Test
	public void givenCardWithoutCvv_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'CVV' is required");
		
		transactionAuthorizationDTOMokc.setCvv(null);
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
	@Test
	public void givenCardWithoutExpirantionDate_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'validade' is required");
		
		transactionAuthorizationDTOMokc.setExpirantionDate(null);
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
	@Test
	public void givenCardWithoutPassword_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'senha' is required");
		
		transactionAuthorizationDTOMokc.setPassword(null);
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
	@Test
	public void givenCardWithoutBalance_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Field 'valor' is required");
		
		transactionAuthorizationDTOMokc.setSaleValue(null);
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
	@Test
	public void givenCardNotExist_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Transaction not authorized. Card invalid.");
		
		Mockito.when(cardRepository.findByCardNumber(transactionAuthorizationDTOMokc.getCardNumber())).thenReturn(Optional.empty());
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
}
