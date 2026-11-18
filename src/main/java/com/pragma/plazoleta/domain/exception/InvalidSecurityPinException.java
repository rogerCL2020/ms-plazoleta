package com.pragma.plazoleta.domain.exception;

public class InvalidSecurityPinException extends RuntimeException {

    public InvalidSecurityPinException() {
        super("El PIN de seguridad es incorrecto");
    }
}