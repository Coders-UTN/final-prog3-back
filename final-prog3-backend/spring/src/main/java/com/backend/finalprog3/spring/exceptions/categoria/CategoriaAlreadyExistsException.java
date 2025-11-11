package com.backend.finalprog3.spring.exceptions.categoria;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class CategoriaAlreadyExistsException extends ApiException {
    public CategoriaAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
