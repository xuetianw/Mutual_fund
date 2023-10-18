package com.mfu.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfu.wallet.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findById(Long customerId);
}
