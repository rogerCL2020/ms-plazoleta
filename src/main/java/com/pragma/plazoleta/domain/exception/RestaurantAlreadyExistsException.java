
package com.pragma.plazoleta.domain.exception;

public class RestaurantAlreadyExistsException extends RuntimeException {

    public RestaurantAlreadyExistsException() {
        super("El restaurante ya existe");
    }
}
