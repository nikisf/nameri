package com.softuni.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Duplicate username!")
public class UserAlreadyExistsException extends RuntimeException{

    private int status;

    public UserAlreadyExistsException() {
        this.status = 409;
    }

    public UserAlreadyExistsException(String message) {
        super(message);
        this.status = 409;
    }

    public int getStatus() {
        return status;
    }
}

