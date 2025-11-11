package com.backend.finalprog3.spring.exceptions.pedido;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class EstadoNoValidoException extends ApiException {
    public EstadoNoValidoException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
