package com.example.mfu.model;


import com.example.mfu.entity.MFDetail;

import java.time.LocalDate;

/* DO NOT CHANGE THIS FILE !!! */
public interface PortfolioProjection {
	Long getPortfolioId();

	Long getCustomerId();

	String getMfName();

	String getMfFundHouse();

	Integer getMfUnitsAvailable();

	String getCurrency();

	LocalDate getTransactionDate();

	MFDetail getMfDetail();
}
