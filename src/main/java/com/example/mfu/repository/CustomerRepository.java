package com.example.mfu.repository;

import com.example.mfu.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	// Find customer by ID
	Optional<Customer> findCustomerByCustomerId(Long customerId);
}

