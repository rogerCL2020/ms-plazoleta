
package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.port.in.CreateEmployeeUseCase;
import com.pragma.plazoleta.application.port.out.EmployeeServicePort;
import com.pragma.plazoleta.application.port.out.RestaurantEmployeeRepositoryPort;
import com.pragma.plazoleta.application.port.out.RestaurantRepositoryPort;
import com.pragma.plazoleta.domain.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.domain.exception.UnauthorizedRestaurantAccessException;
import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.RestaurantEmployee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class EmployeeService implements CreateEmployeeUseCase {

    private final EmployeeServicePort employeeServicePort;
    private final RestaurantEmployeeRepositoryPort restaurantEmployeeRepositoryPort;
    private final RestaurantRepositoryPort restaurantRepositoryPort;

    @Override
    @Transactional
    public Employee create(
            Employee employee,
            String password,
            Long ownerId,
            Long restaurantId) {

        // 1. Validar propietario
        if (ownerId == null || ownerId <= 0) {
            throw new IllegalArgumentException(
                    "El propietario no es válido");
        }

        // 2. Validar restaurante
        if (restaurantId == null || restaurantId <= 0) {
            throw new IllegalArgumentException(
                    "El restaurante no es válido");
        }

        Restaurant restaurant = restaurantRepositoryPort
                .findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);

        // 3. Verificar que el restaurante pertenezca al propietario
        if (!Objects.equals(restaurant.getOwnerId(), ownerId)) {
            throw new UnauthorizedRestaurantAccessException();
        }

        // 4. Validar datos del empleado
        if (employee == null
                || employee.getFirstName() == null
                || employee.getLastName() == null
                || employee.getDocumentNumber() == null
                || employee.getPhone() == null
                || employee.getEmail() == null
                || password == null) {

            throw new IllegalArgumentException(
                    "Los datos del empleado son obligatorios");
        }

        // 5. Verificar correo duplicado
        if (employeeServicePort.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException(
                    "El correo ya está registrado");
        }

        // 6. PRIMERA TRANSACCIÓN LOCAL
        // Crear empleado en ms-usuarios
        Employee createdEmployee =
                employeeServicePort.createEmployee(employee, password);

        try {

            // 7. SEGUNDA TRANSACCIÓN LOCAL
            // Asociar empleado al restaurante en ms-plazoleta
            RestaurantEmployee association = new RestaurantEmployee(
                    null,
                    restaurantId,
                    createdEmployee.getId()
            );

            restaurantEmployeeRepositoryPort.save(association);

            return createdEmployee;

        } catch (RuntimeException exception) {

            // 8. COMPENSACIÓN SAGA
            // Si falla la asociación, eliminar empleado en ms-usuarios
            try {
                employeeServicePort.deleteEmployee(createdEmployee.getId());

            } catch (RuntimeException compensationException) {

                // Registrar el error de compensación
                exception.addSuppressed(compensationException);

                // TODO: Registrar la compensación pendiente
                // para recuperarla mediante un mecanismo de reintentos.
            }

            // Propagar la excepción original
            throw exception;
        }
    }
}
