package com.gpn.banking.card.transaction.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gpn.banking.card.transaction.model.BankTrxTransactionDetails;
import com.gpn.banking.card.transaction.service.BankTrxService;
import com.gpn.banking.card.transaction.view.BankTrxRequest;
import com.gpn.banking.card.transaction.view.BankTrxResponse;

@RestController
@RequestMapping(value = "/banking/trx")
public class BankTrxController {

	private static final Logger log = LoggerFactory.getLogger(BankTrxController.class);
	
	@Autowired
	BankTrxService bankTrxService;
	
	@RequestMapping(value = "/shopping", method = RequestMethod.POST, consumes = {"application/JSON"}, produces = "application/JSON")
	public ResponseEntity<BankTrxResponse> shopping(@Valid @RequestBody BankTrxTransactionDetails request) {
		
		log.info("shopping - Request: " + request);
		
		BankTrxResponse servResponse = bankTrxService.shopping(request);
		ResponseEntity<BankTrxResponse> response = new ResponseEntity<BankTrxResponse>(servResponse, HttpStatus.OK); 

		log.info("shopping - Response: " + response);
		return response;
	}
	
	@RequestMapping(value = "/refund", method = RequestMethod.POST, consumes = {"application/JSON"}, produces = "application/JSON")
	public ResponseEntity<BankTrxResponse> refund(@Valid @RequestBody BankTrxRequest request) {
		
		log.info("refund - Request: " + request);
		
		BankTrxResponse servResponse = bankTrxService.refund(request);
		ResponseEntity<BankTrxResponse> response = new ResponseEntity<BankTrxResponse>(servResponse, HttpStatus.OK);
		
		log.info("refund - Response: " + response);
		return response;
	}
	
	@RequestMapping(value = "/installment", method = RequestMethod.POST, consumes = {"application/JSON"}, produces = "application/JSON")
	public ResponseEntity<BankTrxResponse> installment(@Valid @RequestBody BankTrxTransactionDetails request) {
		
		log.info("installment - Request: " + request);
		
		BankTrxResponse servResponse = bankTrxService.installment(request);
		ResponseEntity<BankTrxResponse> response = new ResponseEntity<BankTrxResponse>(servResponse, HttpStatus.OK);
		
		log.info("installment - Response: " + response);
		return response;
	}
	
	@RequestMapping(value = "/advancepay", method = RequestMethod.POST, consumes = {"application/JSON"}, produces = "application/JSON")
	public ResponseEntity<BankTrxResponse> advancePay(@Valid @RequestBody BankTrxTransactionDetails request) {
		
		log.info("advancePay - Request: " + request);
		
		BankTrxResponse servResponse = bankTrxService.advancePay(request);
		ResponseEntity<BankTrxResponse> response = new ResponseEntity<BankTrxResponse>(servResponse, HttpStatus.OK);
		
		log.info("advancePay - Response: " + response);
		return response;
	}
}
