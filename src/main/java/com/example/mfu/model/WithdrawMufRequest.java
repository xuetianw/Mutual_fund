package com.example.mfu.model;

import lombok.Data;

@Data
public class WithdrawMufRequest {
    private Long customerId;
    private String email;
    private Long schemaId;
    private Integer units;
}
