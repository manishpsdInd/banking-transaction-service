package com.gpn.banking.card.transaction.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "TRANSACTIONDETAILS")
public class BankTrxTransactionDetails {

	@Column(name = "TRANSACTION_ID")
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String transactionId;
	
	@Column(name = "CARD_NUMBER")
	//@NotNull(message = "Card number cannot be null")
	private long cardNumber;
	
	@Column(name = "TRANSACTION_AMOUNT")
	//@Min(value = 1, message = "Transaction amount should not be less than 1")
	private int transactionAmount;
	
	@Column(name = "TRANSACTION_DATE")
	//@NotNull(message = "Transaction date cannot be null")
	private Timestamp transactionDate;
	
	@Column(name = "TRANSACTION_TYPE")
	//@NotNull(message = "Transaction type cannot be null")
	private String transactionType;
	
	@Column(name = "STATUS_CODE")
	//@NotNull(message = "Status code cannot be null")
    private String statusCode;
    
	@Column(name = "STATUS_MESSAGE")
    private String statusMessage; 
	
}