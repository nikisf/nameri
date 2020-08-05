package com.softuni.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Duplicate email")
public class EmailAlreadyExistsException extends RuntimeException {

    private int status;

    public EmailAlreadyExistsException() {
        this.status = 409;
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
        this.status = 409;
    }

    public int getStatus() {
        return status;
    }
}
