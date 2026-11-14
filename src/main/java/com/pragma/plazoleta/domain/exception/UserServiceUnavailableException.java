package com.pragma.plazoleta.domain.exception;

public class UserServiceUnavailableException extends RuntimeException {

    public UserServiceUnavailableException() {
        super("El servicio de usuarios no está disponible");
    }
}