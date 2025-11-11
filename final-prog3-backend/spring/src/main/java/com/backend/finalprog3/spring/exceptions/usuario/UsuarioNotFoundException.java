package com.backend.finalprog3.spring.exceptions.usuario;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UsuarioNotFoundException extends ApiException {
    public UsuarioNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
