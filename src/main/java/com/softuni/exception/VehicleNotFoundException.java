package com.softuni.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Vehicle not found!")
public class VehicleNotFoundException extends RuntimeException {
    private int status;

    public VehicleNotFoundException() {
        this.status = 404;
    }

    public VehicleNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
