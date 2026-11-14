
package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.RestaurantRepositoryPort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.RestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestaurantJpaAdapter implements RestaurantRepositoryPort {

    private final RestaurantJpaRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public boolean existsByNit(String nit) {
        return restaurantRepository.existsByNit(nit);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = restaurantEntityMapper.toEntity(restaurant);
        RestaurantEntity saved = restaurantRepository.save(entity);
        return restaurantEntityMapper.toDomain(saved);
    }

}
