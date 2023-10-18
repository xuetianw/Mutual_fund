package com.mfu.wallet.services;

import java.math.BigDecimal;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.TransactionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mfu.wallet.exception.InsufficientFundsException;
import com.mfu.wallet.model.BalanceResponse;
import com.mfu.wallet.model.CurrencyResponse;
import com.mfu.wallet.model.Transaction;
import com.mfu.wallet.model.Wallet;

public interface WalletService {

	Wallet createWallet(Long customerId) throws InstanceAlreadyExistsException;

	BalanceResponse viewBalance(Long customerId) throws ObjectNotFoundException;
	
	void parseTransaction(int typeId, BigDecimal amount, Long customerId)
			throws TransactionException, ObjectNotFoundException, InsufficientFundsException;

	Wallet addToWallet(Transaction txn);

	Wallet withdrawFromWallet(Transaction txn);

	List<Transaction> viewTransactionHistory(Long customerId) throws ObjectNotFoundException;

	BalanceResponse convertBalance(Long customerId, String previousCurrency, String newCurrency) throws JsonMappingException, JsonProcessingException;

	List<CurrencyResponse> returnCurrencyRates(String body) throws JsonMappingException, JsonProcessingException;

}
