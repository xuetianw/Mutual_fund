package com.example.mfu.repository;

import com.example.mfu.entity.CustomerMFHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMFHistoryRepo extends JpaRepository<CustomerMFHistory, Long> {


    List<CustomerMFHistory> findByCustomerId(Long id);
}
