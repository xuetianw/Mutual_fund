package com.example.mfu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMER_MF_HISTORY")
public class CustomerMFHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "PORTFOLIO_ID")
    private Long portfilioId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "MF_SCHEMA_ID")
    private MFDetail mfDetail;

    @Column(name = "MF_NAME")
    private String mfName;

    @Column(name = "MF_FUND_HOUSE")
    private String mfFundHouse;

    @Column(name = "MF_UNITS")
    private int mfUnits;

    @Column(name = "CURRENCY")
    private String currency;

    @ManyToOne
    @JoinColumn(name = "MF_TRANSACTION_TYPE")
    private TransactionType type;

    @Column(name = "TRANSACTION_DATE")
    private LocalDate localDate;

    @Transient
    private double price;


}
