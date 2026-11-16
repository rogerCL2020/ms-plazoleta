package com.pragma.plazoleta.domain.exception;

public class DishAlreadyExistsException extends RuntimeException {
    public DishAlreadyExistsException() {
        super("The dish already exists in the restaurant with the same name and category");
    }
}
