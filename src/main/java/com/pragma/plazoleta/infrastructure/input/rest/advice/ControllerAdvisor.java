
package com.pragma.plazoleta.infrastructure.input.rest.advice;

import com.pragma.plazoleta.domain.exception.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";


    @ExceptionHandler({RestaurantAlreadyExistsException.class, DishAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleAlreadyExistsException(
            RuntimeException exception) {
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InvalidOwnerException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOwnerException(
            InvalidOwnerException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({RestaurantNotFoundException.class, CategoryNotFoundException.class,DishNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundException(
            RuntimeException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedRestaurantAccessException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedRestaurantAccessException(
            UnauthorizedRestaurantAccessException exception) {
        return buildResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(ActiveOrderAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleActiveOrderAlreadyExistsException(
            ActiveOrderAlreadyExistsException exception) {
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InvalidOrderDishException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOrderDishException(
            InvalidOrderDishException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(
            AccessDeniedException exception) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        boolean isAnonymous = authentication == null
                || authentication instanceof AnonymousAuthenticationToken
                || !authentication.isAuthenticated();

        if (isAnonymous) {
            return buildResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Debes iniciar sesión para acceder a este recurso"
            );
        }

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "No tienes permisos para realizar esta operación"
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDataAccessException(
            DataAccessException exception) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error al procesar la solicitud"
        );
    }

    @ExceptionHandler(InvalidPaginationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPaginationException(
            InvalidPaginationException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserServiceUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleUserServiceUnavailable(
            UserServiceUnavailableException exception) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException exception) {

        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(HttpStatus.BAD_REQUEST, errorMessage);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<Map<String, String>> buildResponse(
            HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Collections.singletonMap(MESSAGE, message));
    }


}
