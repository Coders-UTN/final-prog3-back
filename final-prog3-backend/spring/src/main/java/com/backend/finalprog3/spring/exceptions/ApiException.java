package com.backend.finalprog3.spring.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message); // El mensaje de error
        this.status = status; // El código HTTP
    }

    public HttpStatus getStatus() {
        return status;
    }
}
