package com.mutual_fund.repository;

import com.mutual_fund.entities.Customer_OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Customer_OTP_Repository extends JpaRepository<Customer_OTP, Long> {

    @Query("SELECT C FROM Customer_OTP C WHERE C.customer.id = :id")
    Customer_OTP findByCustomerid(Long id);


    @Query("SELECT C FROM Customer_OTP C WHERE C.customer.email = :email")
    Optional<Customer_OTP> findByCustomerEmail(String email);
}
