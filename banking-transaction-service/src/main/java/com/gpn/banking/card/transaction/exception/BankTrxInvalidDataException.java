package com.gpn.banking.card.transaction.exception;

public class BankTrxInvalidDataException extends BankTrxException{

	private static final long serialVersionUID = -6363456624154837656L;

	public BankTrxInvalidDataException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}

}
