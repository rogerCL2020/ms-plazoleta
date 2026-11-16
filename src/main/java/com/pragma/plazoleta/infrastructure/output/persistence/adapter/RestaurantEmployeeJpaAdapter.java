
package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.RestaurantEmployeeRepositoryPort;
import com.pragma.plazoleta.domain.model.RestaurantEmployee;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.RestaurantEmployeeEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.RestaurantEmployeeEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.RestaurantEmployeeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestaurantEmployeeJpaAdapter implements RestaurantEmployeeRepositoryPort {

    private final RestaurantEmployeeJpaRepository restaurantEmployeeJpaRepository;
    private final RestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Override
    public RestaurantEmployee save(RestaurantEmployee restaurantEmployee) {
        RestaurantEmployeeEntity entity =
                restaurantEmployeeEntityMapper.toEntity(restaurantEmployee);

        RestaurantEmployeeEntity savedEntity =
                restaurantEmployeeJpaRepository.save(entity);

        return restaurantEmployeeEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RestaurantEmployee> findByEmployeeId(Long employeeId) {
        return restaurantEmployeeJpaRepository.findByEmployeeId(employeeId)
                .map(restaurantEmployeeEntityMapper::toDomain);
    }

    @Override
    public boolean existsByEmployeeId(Long employeeId) {
        return restaurantEmployeeJpaRepository.existsByEmployeeId(employeeId);
    }
}
