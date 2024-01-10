package com.mutual_fund.service;

import com.mutual_fund.entities.Wallet;
import com.mutual_fund.repository.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
@Builder
public class WalletService {

    WalletRepository walletRepository;


    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}
