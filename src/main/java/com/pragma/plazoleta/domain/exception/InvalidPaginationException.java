package com.pragma.plazoleta.domain.exception;

public class InvalidPaginationException extends RuntimeException {

    public InvalidPaginationException() {
        super("Los parámetros de paginación no son válidos");
    }
}