package com.mutual_fund.service;


import com.mutual_fund.CustomerReponse.ErrorResponse;
import com.mutual_fund.CustomerReponse.SuccessResponse;
import com.mutual_fund.Exception.UserAlreadyExistException;
import com.mutual_fund.config.EmailSenderService;
import com.mutual_fund.entities.Customer;
import com.mutual_fund.entities.Customer_OTP;
import com.mutual_fund.entities.Wallet;
import com.mutual_fund.model.*;
import com.mutual_fund.repository.CustomerRepository;
import com.mutual_fund.utility.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class ControllerService {
//    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtility jwtUtility;

    private CustomerRepository customerRepository;
    private AuthenticationManager authenticationManager;
    private Customer_OPT_Service customerOptService;
    private EmailSenderService service;
    private UserService userService;
    private WalletService walletService;

    @Autowired
    Random rand;


    @Value("${createWalletUrl}")
    private String createWalletUrl;

    @Autowired
    public ControllerService(CustomerRepository customerRepository, AuthenticationManager authenticationManager,
                             Customer_OPT_Service customerOptService, EmailSenderService service, UserService userService,
                             WalletService walletService) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.customerOptService = customerOptService;
        this.service = service;
        this.userService = userService;
        this.walletService = walletService;
    }


    public ResponseEntity<?> isOPTVerifiedByEmail(String email) {
        Optional<Customer> optCustomer = customerRepository.findByEmail(email);
        if (optCustomer.isEmpty()){
            return new ResponseEntity<>(
                    ErrorResponse.builder().message("user not found")
                            .code(HttpStatus.OK.value()).build(), HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                SuccessResponse.builder().message(
                        new StringBuffer (String.valueOf(customerRepository.findByEmail(email).get().getVerification_status().equals("verified")))).build(),
                HttpStatus.OK);
    }

    public ResponseEntity<?> validateOTP(OTPVerificationRequest otpVerificationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            otpVerificationRequest.getEmail(),
                            otpVerificationRequest.getPassword()
                    )
            );

            Customer customer = customerRepository.findByEmail(otpVerificationRequest.getEmail()).get();
            int OTP = Integer.parseInt(otpVerificationRequest.getOtp());

            // has been verified
            if (customer.getVerification_status().equals("verified")) {
                return new ResponseEntity<>(
                        JwtResponse.builder()
                                .token(jwtUtility.generateToken(customer))
                                .id(customer.getId()).build(),
                        HttpStatus.OK);
                // OPT is correct
            } else if (customerOptService.findOPTbyCustomerId(customer.getId()).getOPT() == OTP) {
                customer.setVerification_status("verified");
                customerRepository.save(customer);

                return new ResponseEntity<>(
                        JwtResponse.builder()
                                .token(jwtUtility.generateToken(customer))
                                .id(customer.getId()).build(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ErrorResponse.builder()
                        .message("wrong opt")
                        .code(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.UNAUTHORIZED);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .message("INVALID_OTP " + e.getMessage())
                    .code(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .message("INVALID_CREDENTIALS " + e.getMessage())
                    .code(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .message("INTERNAL SERVER ERROR " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> registerUser(Customer customer) {
        ErrorResponse errorResponse = new ErrorResponse();
        SuccessResponse registerSuccessResponse = null;
        Customer savedCustomer = new Customer();
        Customer_OTP otp = null;
//        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);

            Optional<Customer> optCustomer = customerRepository.findByEmail(customer.getEmail());

            if (optCustomer.isPresent()) {
                throw new UserAlreadyExistException();
            }

            customer.setVerification_status("NOT_VERIFIED");
            savedCustomer = customerRepository.save(customer);
            log.info("customer " + savedCustomer + "saved");

//            RestTemplate restTemplate = new RestTemplate();
//            log.info("createWalletUrl: " + createWalletUrl);
//            restTemplate.postForLocation(createWalletUrl, savedCustomer.getId());

            Wallet wallet = new Wallet();
            wallet.setCustomer(savedCustomer);
            wallet.setBalance(BigDecimal.ZERO);
            Wallet savedWallet = walletService.saveWallet(wallet);

            log.info("wallet created: for customer: " + savedWallet);

            int OPT_num = rand.nextInt(1000,10000);
            otp = Customer_OTP.builder().customer(savedCustomer).OPT(OPT_num).build();

            customerOptService.addOPT(otp);

            if (savedCustomer.getId() > 0) {
                registerSuccessResponse = new SuccessResponse();
                registerSuccessResponse.setMessage(new StringBuffer("Given user details are successfully registered"));

                service.sendEmail(customer.getEmail(),"OTP", "O TP:" + OPT_num);
            }
//        }

//        catch (MailSendException ex) {
//            registerSuccessResponse.getMessage().append(" an email was not sent to you successfully " + ex.getMessage());
//
//            return new ResponseEntity<>(registerSuccessResponse, HttpStatus.CREATED);
//        }

//        catch (Exception ex) {
//            errorResponse.setMessage("An exception occurred due to " + ex.getMessage());
//            errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//
//            if (savedCustomer != null) {
//                customerRepository.delete(savedCustomer);
//                log.info("customer " + savedCustomer + "deleted");
//            }
//
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return new ResponseEntity<>(registerSuccessResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<?> sendOTP(String email) {
        try {
            if (userService.findByEmail(email).getVerification_status().equals("verified")) {
                throw new RuntimeException("user has been verified");
            }
            Customer_OTP optCustomer = customerOptService.findByCustomerEmail(email);
            int new_OPT_num = rand.nextInt(1000,10000);
            optCustomer.setOPT(new_OPT_num);
            customerOptService.saveUserOTP(optCustomer);
            service.sendEmail(email,"OTP", "O TP:" + new_OPT_num);
        } catch (MailSendException ex) {
            return new ResponseEntity<>(ErrorResponse.builder().message("email not sent :" + ex.getMessage()).code(HttpStatus.OK.value()).build(), HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ErrorResponse.builder().message(ex.getMessage()).code(HttpStatus.OK.value()).build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(SuccessResponse.builder().message(new StringBuffer(" an email was not sent to you successfully")).build(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> authenticate(JwtRequest jwtRequest) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getEmail(),
                            jwtRequest.getPassword()
                    )
            );

            Customer customer = userService.findByEmail(jwtRequest.getEmail());
            if(customer.getVerification_status() == null || !customer.getVerification_status().equals("verified")) {
                throw new BadCredentialsException("OTP not validated yet");
            }

            return new ResponseEntity<>(
                    JwtResponse.builder()
                            .token(jwtUtility.generateToken(customer))
                            .id(customer.getId()).build(),
                    HttpStatus.OK);
    }

    public ResponseEntity<?> validateToken(TokenRequest tokenRequest) {
//        try {
            String email = jwtUtility.getUsernameFromToken(tokenRequest.getToken());

            Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

            jwtUtility.validateToken(tokenRequest.getToken(), customer);

            return new ResponseEntity<>(
                    TokenRespond.builder()
                            .email(customer.getEmail())
                            .id(customer.getId())
                            .build(), HttpStatus.OK);

//        }
//        catch (MalformedJwtException | SignatureException | ExpiredJwtException e) {
//            return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.UNAUTHORIZED);
//        }
    }
}