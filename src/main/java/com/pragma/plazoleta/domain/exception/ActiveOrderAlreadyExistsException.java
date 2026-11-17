package com.pragma.plazoleta.domain.exception;

public class ActiveOrderAlreadyExistsException extends RuntimeException {

    public ActiveOrderAlreadyExistsException() {
        super("El cliente ya tiene un pedido en proceso");
    }
}