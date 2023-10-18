package com.mutual_fund.controller;

import com.mutual_fund.CustomerReponse.ErrorResponse;
import com.mutual_fund.CustomerReponse.SuccessResponse;
import com.mutual_fund.Exception.UserAlreadyExistException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(UserAlreadyExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage("User email already exist");
        errorResponse.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleException(MailSendException ex) {

        SuccessResponse registerSuccessResponse;
        registerSuccessResponse = new SuccessResponse();
        registerSuccessResponse.setMessage(new StringBuffer("Given user details are successfully registered, an email was not sent to you successfully " + ex.getMessage()));

        return new ResponseEntity<>(registerSuccessResponse, HttpStatus.CREATED);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(BadCredentialsException e) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .message("INVALID_CREDENTIALS " + e.getMessage())
                .code(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException2(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("An exception occurred due to " + ex.getMessage());
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(ErrorResponse.builder()
                .message("INTERNAL SERVER ERROR " + ex.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler({MalformedJwtException.class, SignatureException.class, ExpiredJwtException.class})
    public ResponseEntity<?> handleException(Exception e) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("An exception occurred due to " + e.getMessage());
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(ErrorResponse.builder()
                .message("INTERNAL SERVER ERROR " + e.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
