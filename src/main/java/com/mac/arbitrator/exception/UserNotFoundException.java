package com.mac.arbitrator.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public UserNotFoundException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public UserNotFoundException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
