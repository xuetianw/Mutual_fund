package com.example.mfu.controller;

import com.example.mfu.entity.CustomerMFHistory;
import com.example.mfu.model.*;
import com.example.mfu.service.CustomerMFHistoryService;
import com.example.mfu.service.MutualFundPWService;
import com.example.mfu.service.TokenValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/muf")
public class MutualFundController {

    private final MutualFundPWService mutualFundPWService;
    private final TokenValidationService tokenValidationService;
    private final CustomerMFHistoryService customerMFHistoryService;

    public MutualFundController(MutualFundPWService mutualFundPWService,
                                TokenValidationService tokenValidationService, CustomerMFHistoryService customerMFHistoryService) {
        this.mutualFundPWService = mutualFundPWService;
        this.tokenValidationService = tokenValidationService;
        this.customerMFHistoryService = customerMFHistoryService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test API is working";
    }

    @PostMapping("/withdraw_muf")
    public ResponseEntity<?> withdrawMutualFund(@RequestBody WithdrawMufRequest request, @RequestHeader("Authorization") String token) {

        try {
            TokenValidationResponse validationResponse = tokenValidationService.validateToken(token);
                request.setCustomerId(validationResponse.getId());
            request.setEmail(validationResponse.getEmail());

                mutualFundPWService.withdrawMutualFund(request, token);
                return ResponseEntity.ok().body("Successfully withdrew mutual funds from portfolio");
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
//            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/purchase_muf")
    public ResponseEntity<?> purchaseMutualFund(@RequestBody PurchaseMufRequest request, @RequestHeader("Authorization") String token) {
        try {
            TokenValidationResponse validationResponse = tokenValidationService.validateToken(token);
//            TokenValidationResponse validationResponse = new TokenValidationResponse();
//            if (validationResponse.isValid()) {
                request.setCustomerId(validationResponse.getId());
                request.setEmail(validationResponse.getEmail());
                mutualFundPWService.purchaseMutualFund(request, token);
                return ResponseEntity.ok().body("Successfully purchased mutual funds and added to portfolio");
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
//            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/getCustomerMFHistory")
    public ResponseEntity<?> getCustomerMFHistory(@RequestHeader("Authorization") String token) {
        TokenValidationResponse validationResponse = tokenValidationService.validateToken(token);
        Long id = validationResponse.getId();

        List<CustomerMFHistory> list = customerMFHistoryService.getHistoryById(id);


        return new ResponseEntity<>(list, HttpStatus.CREATED);




//        return ResponseEntity.ok(list);

    }
}
