package com.backend.finalprog3.spring.exceptions.usuario;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class IncorrectPasswordException extends ApiException {
    public IncorrectPasswordException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
