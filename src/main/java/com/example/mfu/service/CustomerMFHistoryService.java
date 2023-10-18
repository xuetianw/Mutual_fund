package com.example.mfu.service;

//import com.example.mfu.entity.CustomerMFHistory;
import com.example.mfu.entity.CustomerMFHistory;
import com.example.mfu.repository.CustomerMFHistoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerMFHistoryService {

    private final CustomerMFHistoryRepo customerMFHistoryRepo;

    @Autowired
    public CustomerMFHistoryService(CustomerMFHistoryRepo customerMFHistoryRepo) {
        this.customerMFHistoryRepo = customerMFHistoryRepo;
    }

    public CustomerMFHistory save(CustomerMFHistory customerMFHistory) {
        return customerMFHistoryRepo.save(customerMFHistory);
    }


    public List<CustomerMFHistory> getHistoryById(Long id) {
        List<CustomerMFHistory> list = customerMFHistoryRepo.findByCustomerId(id);
        log.info("customer with id : " + list + "retrived");
        return list;
    }
}
