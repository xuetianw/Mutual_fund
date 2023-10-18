package com.mfu.wallet.rest;

import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mfu.wallet.model.BalanceResponse;
import com.mfu.wallet.model.ConvertRequest;
import com.mfu.wallet.model.CurrencyResponse;
import com.mfu.wallet.model.IdRequest;
import com.mfu.wallet.model.TokenRequest;
import com.mfu.wallet.model.TokenResponse;
import com.mfu.wallet.model.Transaction;
import com.mfu.wallet.model.TransactionRequest;
import com.mfu.wallet.model.Wallet;
import com.mfu.wallet.services.WalletService;

@RestController
@CrossOrigin("*")
@RequestMapping("/wallet")
public class WalletRestController {

	@Value("${auth.url2}")
	String authUrl;

	@Value("${api.url}")
	String apiUrl;

	@Autowired
	private WalletService fundService;

	@Autowired
	private RestTemplate restTemplate;

	//Called on user registration
	@PostMapping("/create")
	public ResponseEntity<String> createWallet(@RequestBody IdRequest idrequest) throws InstanceAlreadyExistsException {
		Wallet wallet = fundService.createWallet(idrequest.getCustomerId());
		return new ResponseEntity<>("Wallet created successfully: " + wallet, HttpStatus.CREATED);
	}

//	@PostMapping("/test")
//	@ResponseBody
//	public BalanceResponse returnBalance(@RequestBody IdRequest idrequest) {
//
//		BalanceResponse balance = fundService.viewBalance(idrequest.getCustomerId());
//		return balance;
//	}

	@PostMapping("/balance")
	@ResponseBody
	public BalanceResponse returnBalance(@RequestBody TokenRequest tokenRequest) {
		System.out.println("YOUR TOKEN REQUEST: " + tokenRequest);
		ResponseEntity<TokenResponse> response = restTemplate.postForEntity(authUrl, tokenRequest, TokenResponse.class);
		TokenResponse tokenResponse = response.getBody();

		BalanceResponse balance = fundService.viewBalance(tokenResponse.getId());
		return balance;
	}

	//Makes a call to a third party API and returns a list of exchange rates depending on base currency
	@GetMapping("/rates/{currencyName}")
	public ResponseEntity<List<CurrencyResponse>> returnRates(@PathVariable String currencyName)
			throws JsonMappingException, JsonProcessingException {
		ResponseEntity<String> reponse = restTemplate.getForEntity(apiUrl + currencyName, String.class);
		String body = reponse.getBody();

		List<CurrencyResponse> currencies = fundService.returnCurrencyRates(body);

		return new ResponseEntity<>(currencies, HttpStatus.OK);
	}

	//Uses the third party API to convert a users balance to the selected currency
	@PostMapping("/convert")
	public ResponseEntity<BalanceResponse> convertCurrency(@RequestBody ConvertRequest convertRequest)
			throws JsonMappingException, JsonProcessingException {
		TokenRequest tokenRequest = convertRequest.getToken();
		ResponseEntity<TokenResponse> response = restTemplate.postForEntity(authUrl, tokenRequest, TokenResponse.class);
		TokenResponse tokenResponse = response.getBody();

		BalanceResponse balance = fundService.convertBalance(tokenResponse.getId(),
				convertRequest.getPreviousCurrency(), convertRequest.getCurrencyName());
		return new ResponseEntity<>(balance, HttpStatus.ACCEPTED);
	}

	//Adds or withdraws from wallet a specified amount by user
	@PostMapping("/transact")
	public ResponseEntity<String> completeTransaction(@RequestBody TransactionRequest transactionRequest) {
		TokenRequest tokenRequest = transactionRequest.getToken();
		ResponseEntity<TokenResponse> response = restTemplate.postForEntity(authUrl, tokenRequest, TokenResponse.class);
		TokenResponse tokenResponse = response.getBody();

		fundService.parseTransaction(transactionRequest.getTypeId(), transactionRequest.getAmount(),
				tokenResponse.getId());
		return new ResponseEntity<>("Transaction completed", HttpStatus.ACCEPTED);
	}
	
	//List of user wallet deposits and withdrawals
	@PostMapping("/history")
	@ResponseBody
	public List<Transaction> returnHistory(@RequestBody TokenRequest tokenRequest) {
		ResponseEntity<TokenResponse> response = restTemplate.postForEntity(authUrl, tokenRequest, TokenResponse.class);
		TokenResponse tokenResponse = response.getBody();

		List<Transaction> result = fundService.viewTransactionHistory(tokenResponse.getId());
		return result;
	}
}
