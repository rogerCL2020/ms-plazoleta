package com.pragma.plazoleta.domain.exception;


public class OrderNotAvailableException extends RuntimeException {

    public OrderNotAvailableException() {
        super("El pedido ya no está disponible para asignación");
    }
}