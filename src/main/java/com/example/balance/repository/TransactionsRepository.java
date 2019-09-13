package com.example.balance.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.balance.model.Accounts;
import com.example.balance.model.Transactions;

public interface TransactionsRepository extends CrudRepository<Transactions, Long>{
	List<Transactions> findByFrom(Accounts from, Pageable pageable);
	List<Transactions> findByTo(Accounts to, Pageable pageable);
	@Query("select t from Transactions t where t.from = :account or t.to = :account")
	List<Transactions> findByFromOrTo(@Param("account") Accounts account, Pageable pageable);		
}
