package com.backend.finalprog3.spring.exceptions.usuario;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApiException {
    public EmailAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
