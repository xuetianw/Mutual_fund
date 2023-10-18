package com.mfu.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfu.wallet.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
	Currency findByCurrencyId(int currencyId);
	
	Currency findByCurrencyName(String currencyName);
}

