package com.pragma.plazoleta.domain.exception;

public class DishNotFoundException extends RuntimeException {

    public DishNotFoundException() {
        super("El plato no existe");
    }
}