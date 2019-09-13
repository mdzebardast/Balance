package com.example.balance.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.balance.model.Accounts;

public interface AccountsRepository extends CrudRepository<Accounts, Long>{
	Optional<Accounts> findByName(String name);
}
