package com.example.balance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.balance.exception.BusinessException;
import com.example.balance.model.Accounts;
import com.example.balance.model.FormatedTransaction;
import com.example.balance.model.Transactions;
import com.example.balance.repository.AccountsRepository;
import com.example.balance.repository.TransactionsRepository;

@Service
public class AccountsService {

	AccountsRepository accountsRepository;
	TransactionsRepository transactionsRepository;

	public AccountsService(AccountsRepository accountsRepository, TransactionsRepository transactionsRepository) {
		this.accountsRepository = accountsRepository;
		this.transactionsRepository = transactionsRepository;
	}

	public Iterable<FormatedTransaction> getTransactionsList(String name, Pageable pageable) throws Exception {
		try {
			Optional<Accounts> account = accountsRepository.findByName(name);
			// Not found
			if (!account.isPresent()) {
				throw new BusinessException("There is no such account.");
			}
			List<Transactions> allTransactions = transactionsRepository.findByFromOrTo(account.get(), pageable);

			// Formatting result
			List<FormatedTransaction> formatedTransactions = new ArrayList<FormatedTransaction>();
			for (Transactions transaction : allTransactions) {
				FormatedTransaction formatedTransaction = new FormatedTransaction();
				formatedTransaction.setBalance(account.get().getBalance());
				formatedTransaction.setFromName(transaction.getFrom().getName());
				formatedTransaction.setToName(transaction.getTo().getName());
				formatedTransaction.setAmount(transaction.getAmount());
				formatedTransaction.setTime(transaction.getTime());

				formatedTransactions.add(formatedTransaction);
			}
			return formatedTransactions;
		} catch (Exception e) {
			throw e;
		}
	}
}
