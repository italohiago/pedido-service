package com.app.pedidoservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PedidoDuplicadoException extends RuntimeException {
    public PedidoDuplicadoException(String message) {
        super(message);
    }
}