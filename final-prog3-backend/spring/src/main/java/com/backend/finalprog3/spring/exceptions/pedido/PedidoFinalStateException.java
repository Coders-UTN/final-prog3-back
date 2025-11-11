package com.backend.finalprog3.spring.exceptions.pedido;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class PedidoFinalStateException extends ApiException {
    public PedidoFinalStateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
