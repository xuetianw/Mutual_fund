package com.mutual_fund.Exception;

public class UserAlreadyExistException extends RuntimeException {



    public UserAlreadyExistException(String msg) {
        super(msg);
    }

    public UserAlreadyExistException() {

    }
}
