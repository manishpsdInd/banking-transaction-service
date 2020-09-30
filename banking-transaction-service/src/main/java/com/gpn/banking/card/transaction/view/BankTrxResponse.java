package com.gpn.banking.card.transaction.view;

import lombok.Data;

@Data
public class BankTrxResponse {

	private String errorCode;
	private String errorMsg;
	private String successCode;
	private String successMsg;
	
}
