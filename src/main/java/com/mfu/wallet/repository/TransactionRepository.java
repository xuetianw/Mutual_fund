package com.mfu.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfu.wallet.model.Customer;
import com.mfu.wallet.model.Transaction;
import com.mfu.wallet.model.Wallet;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	Transaction findById(int id);

	List<Transaction> findByCustomer(Customer customer);

	List<Transaction> findByWallet(Wallet wallet);
}
