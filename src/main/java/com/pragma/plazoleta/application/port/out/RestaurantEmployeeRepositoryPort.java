
package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.RestaurantEmployee;

import java.util.Optional;

public interface RestaurantEmployeeRepositoryPort {

    RestaurantEmployee save(RestaurantEmployee restaurantEmployee);

    Optional<RestaurantEmployee> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndRestaurantId(
            Long employeeId,
            Long restaurantId
    );
}
