package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.port.in.CreateDishUseCase;
import com.pragma.plazoleta.application.port.out.CategoryRepositoryPort;
import com.pragma.plazoleta.application.port.out.DishRepositoryPort;
import com.pragma.plazoleta.application.port.out.RestaurantRepositoryPort;
import com.pragma.plazoleta.domain.exception.CategoryNotFoundException;

import com.pragma.plazoleta.domain.exception.DishAlreadyExistsException;
import com.pragma.plazoleta.domain.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.domain.exception.UnauthorizedRestaurantAccessException;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DishService implements CreateDishUseCase {

    private final DishRepositoryPort dishRepositoryPort;
    private final RestaurantRepositoryPort restaurantRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Dish create(Dish dish, Long ownerId) {

        Restaurant restaurant = restaurantRepositoryPort
                .findById(dish.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);

        if (!Objects.equals(restaurant.getOwnerId(), ownerId)) {
            throw new UnauthorizedRestaurantAccessException();
        }

        if (!categoryRepositoryPort.existsById(dish.getCategoryId())) {
            throw new CategoryNotFoundException();
        }

        boolean existe = dishRepositoryPort
                .existsByNameAndCategoryIdAndRestaurantId(
                        dish.getName(),
                        dish.getCategoryId(),
                        dish.getRestaurantId()
                );

        if (existe) {
            throw new DishAlreadyExistsException();
        }

        dish.setActive(true);

        return dishRepositoryPort.save(dish);
    }


}