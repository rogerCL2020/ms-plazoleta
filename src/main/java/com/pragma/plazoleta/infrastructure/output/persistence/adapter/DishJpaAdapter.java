
package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.DishRepositoryPort;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.DishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.DishJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

}
