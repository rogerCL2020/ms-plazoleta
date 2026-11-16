
package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.RestaurantRepositoryPort;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.RestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantEntityMapper::toDomain);
    }

    @Override
    public PageResult<Restaurant> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "name")
        );

        Page<RestaurantEntity> result = restaurantRepository.findAll(pageable);

        return new PageResult<>(
                result.getContent().stream()
                        .map(restaurantEntityMapper::toDomain)
                        .toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
