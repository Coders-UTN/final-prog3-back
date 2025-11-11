package com.backend.finalprog3.spring.exceptions.categoria;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class CategoriaNotFoundException extends ApiException {
    public CategoriaNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
