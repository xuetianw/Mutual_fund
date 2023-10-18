package com.example.mfu.service;

import com.example.mfu.config.EmailSenderService;
import com.example.mfu.entity.CustomerMFHistory;
import com.example.mfu.entity.Portfolio;
import com.example.mfu.model.*;
import com.example.mfu.repository.CustomerRepository;
import com.example.mfu.repository.PortfolioRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.math.BigDecimal;

@Slf4j
@Service
public class MutualFundPWService {

    private final CustomerRepository customerRepository;
    private final PortfolioRepository portfolioRepository;
    private final WalletInfo walletService;
    private final MutualFundInfoService mutualFundInfoService;
    private final JavaMailSender javaMailSender;

    @Autowired
    private EmailSenderService service;

    @Autowired
    CustomerMFHistoryService customerMFHistoryService;

    @Autowired
    TransactionTypeRepoService transactionTypeRepoService;

//    @PostConstruct
//    public void doMyStartupStuff() {
//        System.out.println("In doMyStartupStuff(): " + getClass().getSimpleName());
//    }

    public MutualFundPWService(CustomerRepository customerRepository,
                               PortfolioRepository portfolioRepository,
                               WalletInfo walletService,
                               MutualFundInfoService mutualFundInfoService,
                               JavaMailSender javaMailSender) {
        this.customerRepository = customerRepository;
        this.portfolioRepository = portfolioRepository;
        this.walletService = walletService;
        this.mutualFundInfoService = mutualFundInfoService;
        this.javaMailSender = javaMailSender;
    }

//    private void sendMail(String subject, String body, String to) {
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(to);
//        mail.setSubject(subject);
//        mail.setText(body);
//        javaMailSender.send(mail);
//    }


    public ResponseEntity<?> purchaseMutualFund(PurchaseMufRequest request, String token) {
        try {
            Long customerId = request.getCustomerId();
            Long schemaId = request.getSchemaId();
            String email = request.getEmail();

            MFDetail mutualFundInfo = mutualFundInfoService.getMutualFundInfo(schemaId);

            if (mutualFundInfo != null) {
                String fundName = mutualFundInfo.getSchemaName();
                Double fundPrice = mutualFundInfo.getCurrPrice();

                int fundUnits = 0;
                double amount = 0.0;

                if (request.getUnits() != 0) {
                    fundUnits = request.getUnits();
                    amount = fundPrice * fundUnits;
                } else if (request.getAmount() != null) {
                    BigDecimal temp = request.getAmount();
                    amount = temp.doubleValue();
                    fundUnits = (int) (amount / fundPrice);
                }

                BigDecimal val = new BigDecimal(Double.toString(amount));

                BigDecimal walletInfo = walletService.getWalletInfo(token);

                if (walletInfo != null) {
                    BigDecimal walletBalance = walletInfo;
                    int comparison = walletBalance.compareTo(val);

                    if (comparison >= 0) {
                        walletService.withdrawWalletFunds(customerId, val, token);
                        Portfolio portfolio = portfolioRepository
                                .findByCustomerCustomerIdAndMfschemaId(customerId, schemaId);

                        if (portfolio == null) {
                            portfolio = new Portfolio();
                            portfolio.setCustomerId(customerId);
                            portfolio.setMfName(fundName);
                            portfolio.setMfFundHouse(mutualFundInfo.getFundHouse());
                            portfolio.setMfUnitsAvailable(fundUnits);
                            portfolio.setCurrency(mutualFundInfo.getSymbol());
                            portfolio.setTransactionDate(LocalDate.now());

                            portfolio.setMfDetail(com.example.mfu.entity.MFDetail.builder()
                                    .schemaId(mutualFundInfo.getSchemaId()).build());
                            portfolioRepository.save(portfolio);
                        } else {
                            portfolio.setMfUnitsAvailable(portfolio.getMfUnitsAvailable() + fundUnits);
                            portfolioRepository.save(portfolio);
                        }
                        if (customerId != null) {

                            ;

                            CustomerMFHistory customerMFHistory = CustomerMFHistory.builder()
                                    .portfilioId(portfolio.getPortfolioId())
                                    .customerId(customerId)
                                    .mfDetail(com.example.mfu.entity.MFDetail.builder().schemaId(schemaId.intValue()).build())
                                    .mfName(mutualFundInfo.getSchemaName())
                                    .mfFundHouse(mutualFundInfo.getFundHouse())
                                    .mfUnits(fundUnits)
                                    .currency(mutualFundInfo.getSymbol())
                                    .type(transactionTypeRepoService.findType(1).get())
                                    .localDate(LocalDate.now()).build();

                            CustomerMFHistory returnedcustomerMFHistory = customerMFHistoryService.save(customerMFHistory);

                            log.info(returnedcustomerMFHistory.toString());


                            service.sendEmail(email, "Purchase Confirmation",
                                    "Thank you for your purchase of " + fundUnits + " units of " + fundName);
                        }

                        return ResponseEntity.ok().body("Successfully purchased mutual funds from wallet");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds in wallet");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mutual fund not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    public ResponseEntity<?> withdrawMutualFund(WithdrawMufRequest request, String token) {
        try {
            Long customerId = request.getCustomerId();
            String email = request.getEmail();
            Integer units = request.getUnits();
            Long schemaId = request.getSchemaId();
            MFDetail mutualFundInfo = mutualFundInfoService.getMutualFundInfo(schemaId);

            if (mutualFundInfo != null) {
                String fundName = mutualFundInfo.getSchemaName();
                Double fundPrice = mutualFundInfo.getCurrPrice();

                Portfolio portfolio = portfolioRepository
                        .findByCustomerCustomerIdAndMfschemaId(customerId, schemaId);


                if (portfolio != null) {
                    if (portfolio.getMfUnitsAvailable() >= units) {
                        double result = fundPrice * units;
                        BigDecimal amount = new BigDecimal(Double.toString(result));

                        walletService.addWalletFunds(customerId, amount, token);

                        portfolio.setMfUnitsAvailable(portfolio.getMfUnitsAvailable() - units);


                        portfolioRepository.save(portfolio);

                        if (customerId != null) {

                            CustomerMFHistory customerMFHistory = CustomerMFHistory.builder()
                                    .portfilioId(portfolio.getPortfolioId())
                                    .customerId(customerId)
                                    .mfDetail(com.example.mfu.entity.MFDetail.builder().schemaId(schemaId.intValue()).build())
//                                    .schemaId(schemaId)
                                    .mfName(mutualFundInfo.getSchemaName())
                                    .mfFundHouse(mutualFundInfo.getFundHouse())
                                    .mfUnits(request.getUnits())
                                    .currency(mutualFundInfo.getSymbol())
                                    .type(transactionTypeRepoService.findType(2).get())
                                    .localDate(LocalDate.now()).build();

                            CustomerMFHistory returnedcustomerMFHistory = customerMFHistoryService.save(customerMFHistory);

                            log.info(returnedcustomerMFHistory.toString());


                            service.sendEmail(email, "Purchase Confirmation",
                                    "Thank you for your purchase of " + request.getUnits() + " units of " + fundName);

                            service.sendEmail(email,
                                    "Withdrawal Confirmation",
                                    "Successfully sold " + units + " units of " + fundName + " for " + amount + ".");
                        }
                        return ResponseEntity.ok().body("Successfully withdrew mutual funds from portfolio");
                    } else {
                        return ResponseEntity.badRequest().body("Not enough units in the portfolio to sell.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mutual Fund not found");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Portfolio not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
