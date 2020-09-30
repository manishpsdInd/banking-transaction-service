package com.gpn.banking.card.transaction.exception;

public class BankTrxException extends Exception	{

	private static final long serialVersionUID = -1891443005820877253L;
	
	private String errCode;
	private String errMsg;
	
	public BankTrxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public BankTrxException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankTrxException(Throwable cause) {
		super(cause);
	}

	public BankTrxException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public BankTrxException(String errMsg) {
		super();
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}