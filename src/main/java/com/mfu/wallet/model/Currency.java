package com.mfu.wallet.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "currency")
@Entity
@NoArgsConstructor
public class Currency {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="currency_id")
	private int currencyId;
	
	@Column(name="currency_name")
	private String currencyName;
	
	@Column(name="currency_symbol")
	private String currencySymbol;
	
	@Column(name="currency_rate")
	private BigDecimal currencyRate;
}

