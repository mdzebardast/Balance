package com.example.balance.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.balance.exception.BusinessException;
import com.example.balance.service.TransferService;

import io.swagger.annotations.Api;

@RestController
@Api(value="Send money", description = "Sending money between two predefined accounts")
public class TransferController {
	
	final BigDecimal MINIMUMTRANSFER = new BigDecimal(0.01).setScale(2,BigDecimal.ROUND_HALF_DOWN);
	TransferService transferService;
	
	@Autowired
	public TransferController(TransferService transferService) {
		super();
		this.transferService = transferService;
	}

	@RequestMapping(value = "/transfer",method = RequestMethod.POST, produces= { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> transfer(@Valid @RequestBody TransferParameter transferParameter){
		try {

			//Calling service	
			boolean flag =transferService.transfer(transferParameter.getFrom(), transferParameter.getTo(), transferParameter.getAmount());
			//result
			if(flag)
				return new ResponseEntity<>("Transfer is successful",HttpStatus.OK);
			else
				return new ResponseEntity<>("Transfer is Not successful",HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(BusinessException bx) {
			return new ResponseEntity<>(bx.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong", e);
		}
	}
}
