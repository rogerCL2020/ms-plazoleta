package com.pragma.plazoleta.domain.exception;


public class OrderNotAvailableException extends RuntimeException {

    public
    OrderNotAvailableException(String message) {
        super(message);
    }
}