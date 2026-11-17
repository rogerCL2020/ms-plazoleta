package com.pragma.plazoleta.domain.exception;

public class InvalidOrderDishException extends RuntimeException {

    public InvalidOrderDishException() {
        super("Uno o más platos no pertenecen al restaurante o no están disponibles");
    }
}