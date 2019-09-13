package com.example.balance.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transactions {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private BigDecimal amount;
	
	@ManyToOne
	private Accounts from;
	
	@ManyToOne
	private Accounts to;
	
	LocalDateTime time;
	
	public Transactions() {
		super();
	}

	public Transactions(BigDecimal amount, Accounts from, Accounts to, LocalDateTime time) {
		super();
		this.amount = amount;
		this.from = from;
		this.to = to;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Accounts getFrom() {
		return from;
	}

	public void setFrom(Accounts from) {
		this.from = from;
	}

	public Accounts getTo() {
		return to;
	}

	public void setTo(Accounts to) {
		this.to = to;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
