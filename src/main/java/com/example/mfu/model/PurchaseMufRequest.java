package com.example.mfu.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseMufRequest {
    private Long customerId;
    private String email;
    private Long schemaId;
    private BigDecimal amount;
    private Integer units;
}
