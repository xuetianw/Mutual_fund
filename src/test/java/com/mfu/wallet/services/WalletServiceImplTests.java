package com.mfu.wallet.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mfu.wallet.exception.CurrencyFormatException;
import com.mfu.wallet.exception.InsufficientFundsException;
import com.mfu.wallet.model.BalanceResponse;
import com.mfu.wallet.model.Customer;
import com.mfu.wallet.model.Transaction;
import com.mfu.wallet.model.Wallet;
import com.mfu.wallet.repository.CurrencyRepository;
import com.mfu.wallet.repository.CustomerRepository;
import com.mfu.wallet.repository.TransactionRepository;
import com.mfu.wallet.repository.WalletRepository;

public class WalletServiceImplTests {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private WalletRepository walletRepository;

	@Mock
	private CurrencyRepository currencyRepository;

	@InjectMocks
	private WalletServiceImpl fundService;

	private Customer testCustomer;

	private Wallet testWallet;

	private Transaction testTxn;

	private List<Transaction> testTxnList;

	@BeforeEach
	public void setUp() throws InstanceAlreadyExistsException {
		MockitoAnnotations.openMocks(this);
		fundService = new WalletServiceImpl(walletRepository, transactionRepository, customerRepository,
				currencyRepository);
		testCustomer = new Customer();
		testCustomer.setId(1L);
		testCustomer.setFname("Test");
		testCustomer.setLname("Testerson");
		testCustomer.setPwd("password");
		testCustomer.setEmail("email@email.com");
		testCustomer.setDateOfBirth(LocalDate.of(2000, 01, 01));

		testWallet = new Wallet();
		testWallet.setId(2L);
		testWallet.setBalance(BigDecimal.valueOf(200.00));
		testWallet.setCustomer(testCustomer);
		testWallet.setCurrencyType("USD");
		testWallet.setCurrencySymbol("$");

		testTxn = new Transaction();
		testTxn.setId(1);
		testTxn.setAmount(BigDecimal.valueOf(100.00));
		testTxn.setDate(LocalDate.now(ZoneId.of("America/Vancouver")));
		testTxn.setTypeId(1);
		testTxn.setCustomer(testCustomer);
		testTxn.setWallet(testWallet);
		testTxnList = new ArrayList<Transaction>();
		testTxnList.add(testTxn);
	}

	@Test
	public void testCreateWallet() throws InstanceAlreadyExistsException {
		when(walletRepository.findByCustomer(testCustomer)).thenReturn(null);
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.save(any(Wallet.class))).thenReturn(testWallet);

		Wallet result = fundService.createWallet(testCustomer.getId());

		Assertions.assertInstanceOf(Wallet.class, result);
		Assertions.assertEquals(testWallet.getBalance(), result.getBalance());
		Assertions.assertEquals(testWallet.getCustomer(), testCustomer);
	}

	@Test
	public void testCreateWalletThatAlreadyExists() {
		when(walletRepository.findByCustomer(testCustomer)).thenReturn(testWallet);
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);

		Assertions.assertThrows(InstanceAlreadyExistsException.class, () -> {
			fundService.createWallet(testCustomer.getId());
		});
	}

	@Test
	public void testViewBalanceAmount() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(any(Customer.class))).thenReturn(testWallet);

		BalanceResponse result = fundService.viewBalance(testCustomer.getId());

		Assertions.assertEquals(result.getBalance(), BigDecimal.valueOf(200.00));
	}

	@Test
	public void testViewBalanceSymbol() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(any(Customer.class))).thenReturn(testWallet);

		BalanceResponse result = fundService.viewBalance(testCustomer.getId());

		Assertions.assertEquals(result.getCurrencySymbol(), testWallet.getCurrencySymbol());
	}

	@Test
	public void testConvertBalanceWithUnnecessaryConversion() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(testCustomer)).thenReturn(testWallet);

		Exception e = assertThrows(CurrencyFormatException.class, () -> fundService.convertBalance(1L, "USD", "USD"));

		Assertions.assertEquals("This is already your current currency type: USD", e.getMessage());
	}

	@Test
	public void testConvertBalanceWithWrongPreviousType() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(testCustomer)).thenReturn(testWallet);

		Exception e = assertThrows(CurrencyFormatException.class, () -> fundService.convertBalance(1L, "CAD", "INR"));

		Assertions.assertEquals("Error: Your previous currency is: USD. Not the entered: CAD", e.getMessage());
	}

	@Test
	public void testViewTransactionHistory() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(transactionRepository.findByCustomer(any(Customer.class))).thenReturn(testTxnList);

		List<Transaction> result = fundService.viewTransactionHistory(testCustomer.getId());

		Assertions.assertEquals(testTxnList, result);
	}

	@Test
	public void testValidParseTransaction() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(any(Customer.class))).thenReturn(testWallet);

		fundService.parseTransaction(1, BigDecimal.valueOf(100.00), 1L);

		verify(transactionRepository).save(any(Transaction.class));
		verify(walletRepository).save(any(Wallet.class));
	}

	@Test
	public void testParseTransactionWithInvalidType() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(any(Customer.class))).thenReturn(testWallet);

		Exception e = assertThrows(TransactionException.class,
				() -> fundService.parseTransaction(3, BigDecimal.valueOf(100.00), 1L));

		Assertions.assertEquals("Invalid transaction type Id.", e.getMessage());
	}

	@Test
	public void testParseTransactionWithZeroAmount() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(any(Customer.class))).thenReturn(testWallet);

		Exception e = assertThrows(TransactionException.class,
				() -> fundService.parseTransaction(1, BigDecimal.valueOf(0.00), 1L));

		Assertions.assertEquals("Amount of transaction must be greater than zero.", e.getMessage());
	};

	@Test
	public void testParseTransactionWithInsufficientFunds() {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(walletRepository.findByCustomer(any(Customer.class))).thenReturn(testWallet);

		Assertions.assertThrows(InsufficientFundsException.class,
				() -> fundService.parseTransaction(2, BigDecimal.valueOf(300.00), 1L));
	}

	@Test
	public void testValidDepositToWallet() throws InstanceAlreadyExistsException {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(transactionRepository.findById(anyInt())).thenReturn(testTxn);
		when(walletRepository.save(any(Wallet.class))).thenReturn(testWallet);

		Wallet result = fundService.createWallet(testCustomer.getId());
		result.setBalance(BigDecimal.ZERO);

		result = fundService.addToWallet(testTxn);

		Assertions.assertEquals(result.getBalance(), BigDecimal.valueOf(100.00));
	}

	@Test
	public void testValidWithdrawalFromWallet() throws InstanceAlreadyExistsException {
		when(customerRepository.findById(anyLong())).thenReturn(testCustomer);
		when(transactionRepository.findById(anyInt())).thenReturn(testTxn);
		when(walletRepository.save(any(Wallet.class))).thenReturn(testWallet);

		testWallet.setBalance(BigDecimal.TEN);
		testTxn.setAmount(BigDecimal.TEN);

		testWallet = fundService.withdrawFromWallet(testTxn);

		Assertions.assertEquals(testWallet.getBalance(), BigDecimal.ZERO);
	}
}
