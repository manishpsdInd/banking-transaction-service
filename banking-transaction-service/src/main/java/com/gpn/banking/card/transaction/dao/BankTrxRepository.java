package com.gpn.banking.card.transaction.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpn.banking.card.transaction.model.BankTrxTransactionDetails;

@Repository
public interface BankTrxRepository  extends JpaRepository<BankTrxTransactionDetails, Integer>{

	BankTrxTransactionDetails findByTransactionId(String transactionId);

}
