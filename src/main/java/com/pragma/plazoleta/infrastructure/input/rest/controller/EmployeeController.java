package com.pragma.plazoleta.infrastructure.input.rest.controller;

import com.pragma.plazoleta.application.dto.request.CreateEmployeeRequest;
import com.pragma.plazoleta.application.dto.result.CreateEmployeeResult;
import com.pragma.plazoleta.application.port.in.CreateEmployeeUseCase;
import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.infrastructure.security.AuthenticatedUser;
import com.pragma.plazoleta.infrastructure.security.AuthenticatedUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @PostMapping
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<CreateEmployeeResult> create(
            @Valid @RequestBody CreateEmployeeRequest request) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Employee employee = new Employee(
                null,
                request.firstName(),
                request.lastName(),
                request.documentNumber(),
                request.phone(),
                request.email()
        );

        CreateEmployeeResult createdEmployee = createEmployeeUseCase.create(
                employee,
                request.password(),
                user.userId(),
                request.restaurantId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEmployee);
    }
}
