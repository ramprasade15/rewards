package com.example.rewards;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Transaction {
	
	private String customerId;
	private LocalDate transactionDate;
	private Double transactionAmount;
	
	

}
