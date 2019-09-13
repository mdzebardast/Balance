package com.example.balance.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.balance.exception.BusinessException;
import com.example.balance.model.FormatedTransaction;
import com.example.balance.service.AccountsService;

import io.swagger.annotations.Api;

@RestController
@Validated
@Api(value="get balance transaction", description = "Account balance and list of transactions")
public class AccountController {

	AccountsService accountsService;

	@Autowired
	public AccountController(AccountsService accountsService) {
		super();
		this.accountsService = accountsService;
	}

	@RequestMapping(value = "/get-transactions/{name}",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getTransactions(
			@PathVariable(value = "name") @NotEmpty(message = "Please provide a Name") String name,
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
		try {
			/*
			 * if(name==null) return new ResponseEntity<Object>("Name Is Empty",
			 * HttpStatus.BAD_REQUEST);
			 */

			Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC , "time");
			
			// Calling service
			Iterable<FormatedTransaction> transactions = accountsService.getTransactionsList(name,pageable);

			return new ResponseEntity<>(transactions, HttpStatus.FOUND);
		}catch(BusinessException bx) {
			return new ResponseEntity<>(bx.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Somthing Went Wrong", e);
		}
	}
}
