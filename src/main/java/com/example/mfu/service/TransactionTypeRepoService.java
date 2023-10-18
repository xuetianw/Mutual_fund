package com.example.mfu.service;

import com.example.mfu.entity.TransactionType;
import com.example.mfu.repository.TransactionTypeRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TransactionTypeRepoService {

    TransactionTypeRepo transactionTypeRepo;

    @Autowired
    public TransactionTypeRepoService(TransactionTypeRepo transactionTypeRepo) {
        this.transactionTypeRepo = transactionTypeRepo;
    }


    @PostConstruct
    public void doMyStartupStuff() {


        saveType(TransactionType.builder().typeId(1).description("purchase").build());
        saveType(TransactionType.builder().typeId(2).description("withdraw").build());

        log.info("type description added");

//        System.out.println("type description added");
    }

    public TransactionType saveType(TransactionType type) {
        return transactionTypeRepo.save(type);
    }

    public Optional<TransactionType> findType(Integer id) {
        return transactionTypeRepo.findById(id);
    }


}
