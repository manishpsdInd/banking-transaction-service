package com.gpn.banking.card.transaction.view;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BankTrxRequest {

	@NotNull(message = "Transaction Id cannot be null")
	private String transactionId;
	
	private String statusMessage; 
	
}
