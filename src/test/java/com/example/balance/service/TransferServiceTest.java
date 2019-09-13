package com.example.balance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.balance.model.Accounts;
import com.example.balance.repository.AccountsRepository;
import com.example.balance.repository.TransactionsRepository;

public class TransferServiceTest {
	
	public TransferServiceTest() {
		super();
	}

	TransferService transferService;
	
	@Mock
	TransactionsRepository transactionsRepository;
	
	@Mock
	AccountsRepository accountsRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		transferService = new TransferService(transactionsRepository, accountsRepository);
	}

	@Test
	public void testTransfer() throws Exception {
		System.out.println("test transfer service with account balance");
		//-given 
		Accounts fromAccount = new Accounts();
		fromAccount.setId(1L);
		fromAccount.setBalance(new BigDecimal(100));
		Optional<Accounts> fromvalue= Optional.of(fromAccount);
		
		Accounts toAccount = new Accounts();
		toAccount.setId(2L);
		toAccount.setBalance(new BigDecimal(50));
		Optional<Accounts> tovalue= Optional.of(toAccount);
		//-when
		
		when(accountsRepository.findById(1L)).thenReturn(fromvalue);
		when(accountsRepository.findById(2L)).thenReturn(tovalue);
		
		//-assert
		assertTrue(transferService.transfer(1L, 2L, new BigDecimal(20))); // transferring money test
		
		assertEquals(fromAccount.getBalance(), new BigDecimal(80));  	  // source balance test
		
		assertEquals(toAccount.getBalance(), new BigDecimal(70));	 	  //destination balance test
		
		//Verify
		verify(accountsRepository, times(1)).findById(1L);
	}

}
