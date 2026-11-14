
package com.pragma.plazoleta.domain.exception;

public class InvalidOwnerException extends RuntimeException {

    public InvalidOwnerException() {
        super("El propietario no es válido");
    }
}
