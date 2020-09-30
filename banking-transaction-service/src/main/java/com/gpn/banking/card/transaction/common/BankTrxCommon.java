package com.gpn.banking.card.transaction.common;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gpn.banking.card.transaction.exception.BankTrxException;
import com.gpn.banking.card.transaction.exception.BankTrxValidationException;
import com.gpn.banking.card.transaction.model.BankTrxTransactionDetails;
import com.gpn.banking.card.transaction.view.BankTrxRequest;

@Service
public class BankTrxCommon {

	public void validateShopping(BankTrxTransactionDetails request) throws BankTrxException {
		
		if(null != request && StringUtils.isEmpty(request.getStatusCode().trim()))	{
			throw new BankTrxValidationException("001", "status code cannot be blank");
		}
		
		if(StringUtils.isEmpty(request.getTransactionType().trim()))	{
			throw new BankTrxValidationException("001", "transaction type cannot be blank");
		}
		
		if(null != request.getTransactionDate())	{
			try {
				request.getTransactionDate().getTime();
			} catch(Exception ex) {
				throw new BankTrxValidationException("001", "transaction date is not valid");
			}
			if(request.getTransactionDate().getTime()<1)
				throw new BankTrxValidationException("001", "transaction date is not valid");
		} else {
			throw new BankTrxValidationException("001", "transaction date cannot be blank");
		}
		
		validateFields(request);

	}
	
	public void validateRefund(BankTrxRequest request) throws BankTrxException {

		if(StringUtils.isEmpty(request.getTransactionId().trim()))	{
			throw new BankTrxValidationException("001", "transaction id cannot be blank or empty");
		} else if(request.getTransactionId().trim().length()!=20 || !request.getTransactionId().trim().contains(BankTrxConstant.TRX_STRING)) {
			throw new BankTrxValidationException("001", "invalid transaction id, cannot process");
		}
	}
	
	public void validateInstallment(BankTrxTransactionDetails request) throws BankTrxException {
		validateFields(request);
	}
	
	public void validateAdvancePay(BankTrxTransactionDetails request) throws BankTrxException {
		validateFields(request);
	}
	
	public String genTrxSeq()	{
		String trxId = BankTrxConstant.TRX_STRING;
		Timestamp ts = new Timestamp(new Date().getTime());
		if(BankTrxConstant.TRX_INCR_SEQ==99) 
			BankTrxConstant.TRX_INCR_SEQ = 0;
		trxId = trxId 
				+ ts.getTime() 
				+ Locale.getDefault().getISO3Country() 
				+ String.format("%02d", ++BankTrxConstant.TRX_INCR_SEQ);
		return trxId;
	}
	
	private void validateFields(BankTrxTransactionDetails request) throws BankTrxException {
		
		if(request.getTransactionAmount() < 1)	{
			throw new BankTrxValidationException("001", "invalid transaction amount, cannot process");
		}
		
		if(request.getCardNumber() < 1L)	{
			throw new BankTrxValidationException("001", "card number is not valid, cannot process");
		}
		
	}
}