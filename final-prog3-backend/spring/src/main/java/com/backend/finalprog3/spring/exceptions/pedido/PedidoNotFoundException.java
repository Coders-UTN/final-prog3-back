package com.backend.finalprog3.spring.exceptions.pedido;

import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class PedidoNotFoundException extends ApiException {
    public PedidoNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
