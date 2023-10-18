package com.mutual_fund.controller;

import com.mutual_fund.CustomerReponse.ErrorResponse;
import com.mutual_fund.CustomerReponse.SuccessResponse;
import com.mutual_fund.Exception.UserAlreadyExistException;
import com.mutual_fund.Exception.UserNotFoundException;
import com.mutual_fund.config.EmailSenderService;
import com.mutual_fund.entities.Customer;
import com.mutual_fund.entities.Customer_OTP;
import com.mutual_fund.model.*;
import com.mutual_fund.repository.CustomerRepository;
import com.mutual_fund.service.ControllerService;
import com.mutual_fund.service.Customer_OPT_Service;
import com.mutual_fund.service.UserService;
import com.mutual_fund.utility.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private ControllerService controllerService;

    @Autowired
    public HomeController(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    @GetMapping("/test")
    public String home() {
        return "homemade";
    }

    @Autowired
    Customer_OPT_Service customerOptService;

    @PostMapping("/isOTPVerified")
    public ResponseEntity<?> isOTPValidated(@RequestParam String email) {
        return controllerService.isOPTVerifiedByEmail(email);
    }

    @PostMapping("/validateOTP")
    public ResponseEntity<?> validateOTP(@RequestBody OTPVerificationRequest otpVerificationRequest){
        return controllerService.validateOTP(otpVerificationRequest);
    }

    @PostMapping("/authenticateV2")
    public ResponseEntity<?> authenticate2(@RequestBody JwtRequest jwtRequest){
        return controllerService.authenticate(jwtRequest);
    }

    @PostMapping("/registerV2")
    public ResponseEntity<?> registerUser2(@RequestBody Customer customer) {
        return controllerService.registerUser(customer);
    }


    @PostMapping("/validateTokenV2")
    public ResponseEntity<?> validate2(@RequestBody TokenRequest tokenRequest) {
        return controllerService.validateToken(tokenRequest);
    }


    @PostMapping("/sendOTP")
    public ResponseEntity<?> sendOTP(@RequestParam String email) {
        return controllerService.sendOTP(email);
    }


    @PostMapping("/test2")
    public String test2() {
        return "ss";
    }


}
