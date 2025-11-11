package com.backend.finalprog3.spring.exceptions.producto;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class ProductoNotFoundException extends ApiException {
    public ProductoNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
