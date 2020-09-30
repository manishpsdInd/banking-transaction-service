package com.gpn.banking.card.transaction.service;

import org.springframework.stereotype.Service;

import com.gpn.banking.card.transaction.model.BankTrxTransactionDetails;
import com.gpn.banking.card.transaction.view.BankTrxRequest;
import com.gpn.banking.card.transaction.view.BankTrxResponse;

@Service
public interface BankTrxService {

	public BankTrxResponse shopping(BankTrxTransactionDetails request);
	
	public BankTrxResponse refund(BankTrxRequest request);
	
	public BankTrxResponse advancePay(BankTrxTransactionDetails request);
	
	public BankTrxResponse installment(BankTrxTransactionDetails request);
	
}
