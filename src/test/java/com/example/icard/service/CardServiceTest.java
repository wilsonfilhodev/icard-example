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
				"5555550434374908", "07/21", "301", "Empresa XYZ", new BigDecimal("1000"), "2684");
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
	public void givenCardWithValidateExpired_whenAuthorizeTransaction_thenReturnExcpetion() throws Exception {
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Transaction not authorized. Card expired.");
		
		transactionAuthorizationDTOMokc.setExpirantionDate("07/18");
		
		Mockito.when(cardRepository.findByCardNumber(transactionAuthorizationDTOMokc.getCardNumber())).thenReturn(Optional.of(new Card()));
		
		cardService.authorizeTransaction(transactionAuthorizationDTOMokc);
	}
	
}
