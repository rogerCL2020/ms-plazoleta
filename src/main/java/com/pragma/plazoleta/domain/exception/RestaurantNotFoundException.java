package com.pragma.plazoleta.domain.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException() {
        super("El restaurante no existe");
    }
}