package com.example.balance.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.balance.exception.BusinessException;
import com.example.balance.model.Accounts;
import com.example.balance.model.Transactions;
import com.example.balance.repository.AccountsRepository;
import com.example.balance.repository.TransactionsRepository;

@Service
public class TransferService {

	TransactionsRepository transactionsRepository;
	AccountsRepository accountsRepository;

	public TransferService(TransactionsRepository transactionsRepository, AccountsRepository accountsRepository) {
		this.transactionsRepository = transactionsRepository;
		this.accountsRepository = accountsRepository;
	}

	@Transactional
	public boolean transfer(Long from, Long to, BigDecimal amount) throws Exception {
		try {
			if (from == to) {
				throw new BusinessException("It's illegal to transfer from your account to yourself.");
			}

			Optional<Accounts> fromAccount = accountsRepository.findById(from);
			Optional<Accounts> toAccount = accountsRepository.findById(to);

			if (!fromAccount.isPresent() && !toAccount.isPresent()) {
				throw new BusinessException("From Account or To account is not valid.");
			}
			if (fromAccount.get().getBalance().compareTo(amount) <0) {
				throw new BusinessException("There is no enough money.");
			}
			// Transferring money
			Transactions transaction = new Transactions();
			transaction.setFrom(fromAccount.get());
			transaction.setTo(toAccount.get());
			transaction.setAmount(amount);
			transaction.setTime(LocalDateTime.now());

			transactionsRepository.save(transaction);
			// Subtracting from source
			fromAccount.get().setBalance(fromAccount.get().getBalance().subtract(amount));
			accountsRepository.save(fromAccount.get());
			// Adding to destination
			toAccount.get().setBalance(toAccount.get().getBalance().add(amount));
			accountsRepository.save(toAccount.get());

			return true;
		} catch (Exception e) {
			throw e;
		}
	}
}
