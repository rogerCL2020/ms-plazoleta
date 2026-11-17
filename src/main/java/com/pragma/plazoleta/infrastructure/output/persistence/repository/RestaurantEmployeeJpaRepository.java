
package com.pragma.plazoleta.infrastructure.output.persistence.repository;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantEmployeeJpaRepository
        extends JpaRepository<RestaurantEmployeeEntity, Long> {

    Optional<RestaurantEmployeeEntity> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndRestaurantId(
            Long employeeId,
            Long restaurantId
    );
}
