package com.gpn.banking.card.transaction.exception;

public class BankTrxValidationException extends BankTrxException{

	private static final long serialVersionUID = -6363456624145837656L;

	public BankTrxValidationException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}

}
