package com.example.balance.bootstrap;

import java.math.BigDecimal;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.balance.model.Accounts;
import com.example.balance.repository.AccountsRepository;
import com.example.balance.service.TransferService;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	private AccountsRepository accountsRepository;
	
	private TransferService transferService; 
	
	public DevBootstrap(AccountsRepository accountsRepository, TransferService transferService) {
		super();
		this.accountsRepository = accountsRepository;
		this.transferService = transferService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Accounts mohammadAccount=new Accounts("Mohammad", new BigDecimal(1000));
		accountsRepository.save(mohammadAccount);
		
		Accounts GailiAccount=new Accounts("Gaili", new BigDecimal(800));
		accountsRepository.save(GailiAccount);
		
		Accounts FarhadAccount=new Accounts("Farhad", new BigDecimal(900));
		accountsRepository.save(FarhadAccount);
		
		try {
			transferService.transfer(mohammadAccount.getId(), GailiAccount.getId(), new BigDecimal(1));
			transferService.transfer(mohammadAccount.getId(), GailiAccount.getId(), new BigDecimal(2));
			transferService.transfer(mohammadAccount.getId(), GailiAccount.getId(), new BigDecimal(3));
			transferService.transfer(GailiAccount.getId(), mohammadAccount.getId(), new BigDecimal(11));
			transferService.transfer(GailiAccount.getId(), mohammadAccount.getId(), new BigDecimal(12));
			transferService.transfer(GailiAccount.getId(), mohammadAccount.getId(), new BigDecimal(13));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Transactions transactions = new Transactions(new BigDecimal(1),mohammadAccount,GailiAccount, LocalDateTime.now());
		transactionsRepository.save(transactions);
		
		Transactions transactions2 = new Transactions(new BigDecimal(2),mohammadAccount,GailiAccount, LocalDateTime.now());
		transactionsRepository.save(transactions2);
		
		Transactions transactions3 = new Transactions(new BigDecimal(3),mohammadAccount,GailiAccount, LocalDateTime.now());
		transactionsRepository.save(transactions3);
		
		Transactions transaction11 = new Transactions(new BigDecimal(11),GailiAccount, mohammadAccount, LocalDateTime.now());
		transactionsRepository.save(transaction11);
		
		Transactions transactions12 = new Transactions(new BigDecimal(12),GailiAccount, mohammadAccount, LocalDateTime.now());
		transactionsRepository.save(transactions12);
		
		Transactions transactions13 = new Transactions(new BigDecimal(13),GailiAccount, mohammadAccount,LocalDateTime.now());
		transactionsRepository.save(transactions13);*/
		
		System.out.println("Database has been initialized...");
		
	}

}
