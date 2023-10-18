package com.mutual_fund.repository;

import com.mutual_fund.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    @Query("SELECT C FROM Customer C WHERE C.email = :email")
    Optional<Customer> findByEmail(String email);
}
