package com.pragma.plazoleta.domain.exception;

public class UnauthorizedRestaurantAccessException extends RuntimeException {

    public UnauthorizedRestaurantAccessException() {
        super("No tienes permisos para gestionar este restaurante");
    }
}