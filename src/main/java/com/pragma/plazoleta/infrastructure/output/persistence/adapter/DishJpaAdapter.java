
package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.DishRepositoryPort;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.DishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.DishJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements DishRepositoryPort {

    private final DishJpaRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public Dish save(Dish dish) {
        DishEntity entity = dishEntityMapper.toEntity(dish);
        DishEntity saved = dishRepository.save(entity);

        return dishEntityMapper.toDomain(saved);
    }

    @Override
    public boolean existsByNameAndCategoryIdAndRestaurantId(String name, Long categoryId, Long restaurantId) {
        return dishRepository.existsByNameAndCategoryIdAndRestaurantId(name, categoryId, restaurantId);
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return dishRepository.findById(id)
                .map(dishEntityMapper::toDomain);
    }

    @Override
    public PageResult<Dish> findByRestaurant(
            Long restaurantId,
            Long categoryId,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<DishEntity> result = dishRepository.findByRestaurant(
                restaurantId,
                categoryId,
                pageable
        );

        return new PageResult<>(
                result.getContent().stream()
                        .map(dishEntityMapper::toDomain)
                        .toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
