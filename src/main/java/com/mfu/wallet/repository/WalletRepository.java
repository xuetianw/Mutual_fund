package com.mfu.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfu.wallet.model.Customer;
import com.mfu.wallet.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	Wallet findById(int id);

	Wallet findByCustomer(Customer customer);
}
