package com.example.balance.controller;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransferParameter {
	@NotNull(message = "Please provide a from id. Minimum is 1")
	@Min(value = 1, message = "Please provide a from id. Minimum is 1")
	private Long from;
	@NotNull(message = "Please provide a to id. Minimum is 1")
	@Min(value = 1, message = "Please provide a to id. Minimum is 1")
	private Long to;
	@NotNull(message = "Please provide valid amount. Greater than zero")
	@DecimalMin(value = "0.01", message = "Please provide valid amount. Greater than zero")
	private BigDecimal amount;
	
	
	public TransferParameter() {
		super();
	}
	
	public TransferParameter(Long from, Long to, BigDecimal amount) {
		super();
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getTo() {
		return to;
	}
	public void setTo(Long to) {
		this.to = to;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
