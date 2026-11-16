package com.pragma.plazoleta.domain.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("La categoría no existe");
    }
}