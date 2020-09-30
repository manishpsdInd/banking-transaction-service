package com.gpn.banking.card.transaction.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gpn.banking.card.transaction.common.BankTrxCommon;
import com.gpn.banking.card.transaction.common.BankTrxConstant;
import com.gpn.banking.card.transaction.dao.BankTrxRepository;
import com.gpn.banking.card.transaction.exception.BankTrxException;
import com.gpn.banking.card.transaction.exception.BankTrxInvalidDataException;
import com.gpn.banking.card.transaction.model.BankTrxTransactionDetails;
import com.gpn.banking.card.transaction.service.BankTrxService;
import com.gpn.banking.card.transaction.view.BankTrxRequest;
import com.gpn.banking.card.transaction.view.BankTrxResponse;

@Service
public class BankTrxServiceImpl implements BankTrxService {

	private static final Logger log = LoggerFactory.getLogger(BankTrxServiceImpl.class);
	
	@Autowired
	BankTrxCommon bankTrxCommon;
	
	@Autowired
	BankTrxRepository bankTrxRepository;
	
	@Override
	@Transactional
	public BankTrxResponse shopping(BankTrxTransactionDetails request) {

		log.info("shopping - Entry");
		
		BankTrxResponse response = new BankTrxResponse();
		try	{
			
			// Validating request
			bankTrxCommon.validateShopping(request);
		
			// Setting generated Transaction ID
			request.setTransactionId(bankTrxCommon.genTrxSeq());
			
			// Calling Repository to save record
			BankTrxTransactionDetails oldTrxData = bankTrxRepository.save(request);
			
			// Creating success response
			response.setSuccessCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setSuccessMsg("Record inserted with TrxId: " + oldTrxData.getTransactionId());
			response.setErrorCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setErrorMsg(BankTrxConstant.SUCCESS_TXT);

		}	catch(BankTrxException btEx)	{
			log.error("shopping - Error: ", btEx);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(btEx.getErrMsg());
			
		}   catch(Exception ex)	{
			log.error("shopping - Error: ", ex);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(ex.getMessage());
			
		}
	
		log.info("shopping - Exit");
		return response;
	}

	@Override
	@Transactional
	public BankTrxResponse refund(BankTrxRequest request) {
		
		BankTrxResponse response = new BankTrxResponse();
		try	{
			
			// Validating request
			bankTrxCommon.validateRefund(request);
		
			// Calling Repository to fetch record
			BankTrxTransactionDetails oldTrxData = bankTrxRepository.findByTransactionId(request.getTransactionId());
			
			if(null==oldTrxData)
				throw new BankTrxInvalidDataException("002", "no record found for given transaction id");
			if(!oldTrxData.getStatusCode().equalsIgnoreCase(BankTrxConstant.STS_CODE_DECLINED))
				throw new BankTrxInvalidDataException("002", "validation failed - previous transaction is not Declined");
			
			oldTrxData.setStatusCode(BankTrxConstant.STS_CODE_APPROVED);
			
			// Calling Repository to update old record
			BankTrxTransactionDetails updatedOldTrxData = bankTrxRepository.save(oldTrxData);
			
			BankTrxTransactionDetails data = new BankTrxTransactionDetails();
			data.setCardNumber(oldTrxData.getCardNumber());
			data.setStatusCode(BankTrxConstant.STS_CODE_APPROVED);
			data.setStatusMessage("Ref. trxId: " + updatedOldTrxData.getTransactionId());
			data.setTransactionAmount(oldTrxData.getTransactionAmount());
			data.setTransactionDate(new Timestamp(new Date().getTime()));
			data.setTransactionId(bankTrxCommon.genTrxSeq());
			data.setTransactionType(BankTrxConstant.TRX_TYPE_CREDIT);
			
			// Calling Repository to save new record
			BankTrxTransactionDetails newTrxData = bankTrxRepository.save(data);
			
			// Creating success response
			response.setSuccessCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setSuccessMsg("Record inserted with TrxId: " + newTrxData.getTransactionId());
			response.setErrorCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setErrorMsg(BankTrxConstant.SUCCESS_TXT);

		}	catch(BankTrxException btEx)	{
			log.error("shopping - Error: ", btEx);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(btEx.getErrMsg());
			
		}   catch(Exception ex)	{
			log.error("shopping - Error: ", ex);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(ex.getMessage());
			
		}
	
		log.info("shopping - Exit");
		return response;
	}

	@Override
	@Transactional
	public BankTrxResponse installment(BankTrxTransactionDetails request) {

		log.info("installment - Entry");
		
		BankTrxResponse response = new BankTrxResponse();
		try	{
			
			// Validating request
			bankTrxCommon.validateInstallment(request);
		
			// Setting generated Transaction ID
			request.setTransactionId(bankTrxCommon.genTrxSeq());
			
			// Set Installment in Status Message
			request.setStatusMessage(BankTrxConstant.STS_MSG_INSTALLMENT);
			
			// Setting values for non mandatory fields
			if(null==request.getTransactionDate()) {
				request.setTransactionDate(new Timestamp(new Date().getTime()));
			}
			if(null==request.getTransactionType() || StringUtils.isEmpty(request.getTransactionType().trim())) {
				request.setTransactionType(BankTrxConstant.TRX_TYPE_DEBIT);
			}
			if(null==request.getStatusCode() || StringUtils.isEmpty(request.getStatusCode().trim())) {
					request.setStatusCode(BankTrxConstant.STS_CODE_APPROVED);
			}
			
			// Calling Repository to save record
			BankTrxTransactionDetails oldTrxData = bankTrxRepository.save(request);
			
			// Creating success response
			response.setSuccessCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setSuccessMsg("Record inserted with TrxId: " + oldTrxData.getTransactionId());
			response.setErrorCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setErrorMsg(BankTrxConstant.SUCCESS_TXT);

		}	catch(BankTrxException btEx)	{
			log.error("installment - Error: ", btEx);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(btEx.getErrMsg());
			
		}   catch(Exception ex)	{
			log.error("installment - Error: ", ex);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(ex.getMessage());
			
		}
	
		log.info("installment - Exit");
		return response;
	}
	
	@Override
	@Transactional
	public BankTrxResponse advancePay(BankTrxTransactionDetails request) {

		log.info("advancePay - Entry");
		
		BankTrxResponse response = new BankTrxResponse();
		try	{
			
			// Validating request
			bankTrxCommon.validateAdvancePay(request);
		
			// Setting generated Transaction ID
			request.setTransactionId(bankTrxCommon.genTrxSeq());
			
			// Set Advance Payment in Status Message
			request.setStatusMessage(BankTrxConstant.STS_MSG_ADVANCE_PAYMENT);
			
			// Setting values for non mandatory fields 
			if(null==request.getTransactionDate()) {
				request.setTransactionDate(new Timestamp(new Date().getTime()));
			}
			
			if(null==request.getTransactionType() || StringUtils.isEmpty(request.getTransactionType().trim())) {
				request.setTransactionType(BankTrxConstant.TRX_TYPE_DEBIT);
			}
			if(null==request.getStatusCode() || StringUtils.isEmpty(request.getStatusCode().trim())) {
				request.setStatusCode(BankTrxConstant.STS_CODE_APPROVED);
			}
			
			// Calling Repository to save record
			BankTrxTransactionDetails oldTrxData = bankTrxRepository.save(request);
			
			// Creating success response
			response.setSuccessCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setSuccessMsg("Record inserted with TrxId: " + oldTrxData.getTransactionId());
			response.setErrorCode(BankTrxConstant.RESP_CODE_SUCCESS);
			response.setErrorMsg(BankTrxConstant.SUCCESS_TXT);

		}	catch(BankTrxException btEx)	{
			log.error("advancePay - Error: ", btEx);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(btEx.getErrMsg());
			
		}   catch(Exception ex)	{
			log.error("advancePay - Error: ", ex);
			
			response.setSuccessCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setSuccessMsg(BankTrxConstant.FAILURE_TXT);
			response.setErrorCode(BankTrxConstant.RESP_CODE_FAILURE);
			response.setErrorMsg(ex.getMessage());
			
		}
	
		log.info("advancePay - Exit");
		return response;
	}
}
