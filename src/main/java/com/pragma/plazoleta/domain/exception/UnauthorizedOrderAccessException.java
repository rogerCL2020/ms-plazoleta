package com.pragma.plazoleta.domain.exception;

public class UnauthorizedOrderAccessException extends RuntimeException {

    public UnauthorizedOrderAccessException() {
        super("No tienes permiso para acceder a este pedido");
    }
}