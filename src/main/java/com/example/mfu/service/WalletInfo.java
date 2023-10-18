package com.example.mfu.service;

import com.example.mfu.model.WalletInfoResp;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class WalletInfo {
    private BigDecimal walletBalance;

    @Value("${wallet_service}")
    private String wallet_serviceURL;

    public BigDecimal getWalletInfo(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        WebClient client = WebClient.create();
        String url = wallet_serviceURL + "/wallet/balance";

        Map<String, Object> getWallet = new HashMap<>();
        getWallet.put("token", token);

        ResponseEntity<WalletInfoResp> response = client.post()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(getWallet)
                .retrieve()
                .toEntity(WalletInfoResp.class)
                .block();

        WalletInfoResp walletInfo = response.getBody();

        if (walletInfo != null) {
            this.walletBalance = walletInfo.getBalance();
            return walletBalance;
        } else {
            return null;
        }
    }
    
    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public ResponseEntity<String> addWalletFunds(Long customerId, BigDecimal amount, String token) {
        WebClient client = WebClient.create();
        String url = wallet_serviceURL + "/wallet/transact";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("typeId", 1);
        transaction.put("description", "Deposit");
        transaction.put("amount", amount);
        transaction.put("customerId", customerId);
        transaction.put("token", token);
    
        ResponseEntity<String> response = client.post()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(transaction)
                .retrieve()
                .toEntity(String.class)
                .block();
    
        return response;
    }

    public ResponseEntity<String> withdrawWalletFunds(Long customerId, BigDecimal amount, String token) {
        WebClient client = WebClient.create();
        String url = wallet_serviceURL + "/wallet/transact";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("typeId", 2);
        transaction.put("description", "Withdrawal");
        transaction.put("amount", amount);
        transaction.put("customerId", customerId);
        transaction.put("token", token);
    
        ResponseEntity<String> response = client.post()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(transaction)
                .retrieve()
                .toEntity(String.class)
                .block();
        return response;
    }
}
