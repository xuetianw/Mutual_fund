package com.mfu.wallet.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfu.wallet.exception.CurrencyFormatException;
import com.mfu.wallet.exception.InsufficientFundsException;
import com.mfu.wallet.model.BalanceResponse;
import com.mfu.wallet.model.Currency;
import com.mfu.wallet.model.CurrencyResponse;
import com.mfu.wallet.model.Customer;
import com.mfu.wallet.model.Transaction;
import com.mfu.wallet.model.Wallet;
import com.mfu.wallet.repository.CurrencyRepository;
import com.mfu.wallet.repository.CustomerRepository;
import com.mfu.wallet.repository.TransactionRepository;
import com.mfu.wallet.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${api.url}")
	String apiUrl;

	// Mocked Customer object
//	@Override
//	public Customer createCustomer() {
//		Customer customer = new Customer();
//		customer.setFname(RandomStringUtils.randomAlphabetic(6));
//		customer.setLname(RandomStringUtils.randomAlphabetic(6));
//		customer.setEmail(RandomStringUtils.randomAlphabetic(7));
//		customer.setPwd((RandomStringUtils.randomAlphabetic(7)));
//		customer.setDateOfBirth(LocalDate.of(2000, 01, 01));
//		Customer createdCustomer = customerRepository.save(customer);
//		return createdCustomer;
//	}

	@Override
	public Wallet createWallet(Long customerId) throws InstanceAlreadyExistsException {
		Customer customer = customerRepository.findById(customerId);

		if (customer == null) {
			throw new ObjectNotFoundException("There is no customer with id: ", customerId);
		}
		if (walletRepository.findByCustomer(customer) != null) {
			throw new InstanceAlreadyExistsException("Customer already has a wallet");
		}

		Wallet wallet = new Wallet();
		wallet.setCustomer(customer);
		wallet.setBalance(BigDecimal.ZERO);
		Wallet createdWallet = walletRepository.save(wallet);
		return createdWallet;
	}

	@Override
	public BalanceResponse viewBalance(Long customerId) throws ObjectNotFoundException {
		Customer customer = customerRepository.findById(customerId);
		if (customer == null) {
			throw new ObjectNotFoundException("There is no customer with id: ", customerId);
		}
		Wallet wallet = walletRepository.findByCustomer(customer);
		BalanceResponse balanceResponse = new BalanceResponse(wallet.getBalance(), wallet.getCurrencySymbol());
		return balanceResponse;
	}

	@Override
	public BalanceResponse convertBalance(Long customerId, String previousCurrency, String newCurrency)
			throws JsonMappingException, JsonProcessingException {

		Customer customer = customerRepository.findById(customerId);
		Wallet wallet = walletRepository.findByCustomer(customer);
		String result = wallet.getCurrencyType();

		if (result.equals(newCurrency)) {
			throw new CurrencyFormatException("This is already your current currency type: " + newCurrency);
		} else if (!result.equals(previousCurrency)) {
			throw new CurrencyFormatException(
					"Error: Your previous currency is: " + result + ". Not the entered: " + previousCurrency);
		} else if (previousCurrency.equals(newCurrency)) {
			throw new CurrencyFormatException("Currencies must be different");
		}

		ResponseEntity<String> reponse = restTemplate.getForEntity(apiUrl + previousCurrency, String.class);
		String body = reponse.getBody();
		CurrencyResponse currencyValue = new CurrencyResponse();

		List<CurrencyResponse> callResponses = this.returnCurrencyRates(body);
		for (CurrencyResponse callResp : callResponses) {
			if (callResp.getCode().equals(newCurrency)) {
				currencyValue = callResp;
			}
			;
		}

		BigDecimal balance = wallet.getBalance();
		BigDecimal newBalance = balance.multiply(BigDecimal.valueOf(currencyValue.getValue()));
		Currency currency = currencyRepository.findByCurrencyName(currencyValue.getCode());

		wallet.setBalance(newBalance);
		wallet.setCurrencySymbol(currency.getCurrencySymbol());
		wallet.setCurrencyType(currency.getCurrencyName());
		walletRepository.save(wallet);
		BalanceResponse balanceResponse = this.viewBalance(customerId);
		return balanceResponse;
	}

	@Override
	public void parseTransaction(int typeId, BigDecimal amount, Long customerId)
			throws TransactionException, ObjectNotFoundException, InsufficientFundsException {

		if ((BigDecimal.ZERO).compareTo(amount) >= 0) {
			throw new TransactionException("Amount of transaction must be greater than zero.");
		}

		if (typeId != 1 && typeId != 2) {
			throw new TransactionException("Invalid transaction type Id.");
		}

		Transaction txn = new Transaction();
		txn.setTypeId(typeId);
		txn.setAmount(amount);
		txn.setDate(LocalDate.now(ZoneId.of("America/Vancouver")));
		txn.setDescription(txn.getDescription());
		Customer customer = customerRepository.findById(customerId);
		Wallet wallet = walletRepository.findByCustomer(customer);
		txn.setCustomer(customer);
		txn.setWallet(wallet);

		if (customer == null) {
			throw new ObjectNotFoundException("There is no customer with id: ", customerId);
		}

		if (typeId == 1) {
			addToWallet(txn);
		} else {
			if (amount.compareTo(wallet.getBalance()) > 0) {
				throw new InsufficientFundsException("Insufficient funds for withdrawal");
			}
			withdrawFromWallet(txn);
		}
	}

	@Override
	public Wallet addToWallet(Transaction txn) {
		Wallet wallet = txn.getWallet();
		wallet.setCustomer(txn.getCustomer());
		wallet.setBalance(wallet.getBalance().add(txn.getAmount()));

		transactionRepository.save(txn);

		Wallet updatedWallet = walletRepository.save(wallet);
		return updatedWallet;
	}

	@Override
	public Wallet withdrawFromWallet(Transaction txn) {
		Wallet wallet = txn.getWallet();
		wallet.setCustomer(txn.getCustomer());
		wallet.setBalance(wallet.getBalance().subtract(txn.getAmount()));

		transactionRepository.save(txn);

		Wallet updatedWallet = walletRepository.save(wallet);
		return updatedWallet;
	}

	@Override
	public List<Transaction> viewTransactionHistory(Long customerId) throws ObjectNotFoundException {
		Customer customer = customerRepository.findById(customerId);
		if (customer == null) {
			throw new ObjectNotFoundException("There is no customer with the id: ", customerId);
		}
		List<Transaction> txnHistory = transactionRepository.findByCustomer(customer);
		return txnHistory;
	}

	@Override
	public List<CurrencyResponse> returnCurrencyRates(String body)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper map = new ObjectMapper();
		List<CurrencyResponse> currencies = new ArrayList<>();
		JsonNode node = map.readTree(body);
		JsonNode noder = node.get("data");
		Iterator<JsonNode> iterator = noder.iterator();

		while (iterator.hasNext()) {
			JsonNode entry = iterator.next();
			JsonNode names = entry.get("code");
			JsonNode values = entry.get("value");
			CurrencyResponse currencyResponse = new CurrencyResponse(names.asText(), values.asDouble());
			currencies.add(currencyResponse);
		}
		return currencies;
	}

	public WalletServiceImpl(WalletRepository walletRepo, TransactionRepository transactionRepository,
			CustomerRepository customerRepository, CurrencyRepository currencyRepository) {
		this.walletRepository = walletRepo;
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.currencyRepository = currencyRepository;
	}
}
