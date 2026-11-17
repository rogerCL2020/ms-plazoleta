package com.pragma.plazoleta.domain.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("El pedido no existe");
    }
}