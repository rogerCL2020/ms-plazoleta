package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.port.in.ChangeDishStatusUseCase;
import com.pragma.plazoleta.application.port.in.CreateDishUseCase;
import com.pragma.plazoleta.application.port.in.ListDishesUseCase;
import com.pragma.plazoleta.application.port.in.UpdateDishUseCase;
import com.pragma.plazoleta.application.port.out.CategoryRepositoryPort;
import com.pragma.plazoleta.application.port.out.DishRepositoryPort;
import com.pragma.plazoleta.application.port.out.RestaurantRepositoryPort;
import com.pragma.plazoleta.domain.exception.*;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.Restaurant;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DishService implements CreateDishUseCase, UpdateDishUseCase, ChangeDishStatusUseCase, ListDishesUseCase {

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

    @Override
    public Dish update(Long dishId, BigDecimal price, String description, Long ownerId) {

        Dish dish = dishRepositoryPort.findById(dishId)
                .orElseThrow(DishNotFoundException::new);

        Restaurant restaurant = restaurantRepositoryPort
                .findById(dish.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);

        if (!Objects.equals(restaurant.getOwnerId(), ownerId)) {
            throw new UnauthorizedRestaurantAccessException();
        }

        dish.setPrice(price);
        dish.setDescription(description);

        return dishRepositoryPort.save(dish);
    }


    @Override
    public Dish changeStatus(Long dishId, Boolean active, Long ownerId) {

        Dish dish = dishRepositoryPort.findById(dishId)
                .orElseThrow(DishNotFoundException::new);

        Restaurant restaurant = restaurantRepositoryPort
                .findById(dish.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);

        if (!Objects.equals(restaurant.getOwnerId(), ownerId)) {
            throw new UnauthorizedRestaurantAccessException();
        }

        dish.setActive(active);

        return dishRepositoryPort.save(dish);
    }

    @Override
    public PageResult<Dish> listByRestaurant(
            Long restaurantId,
            Long categoryId,
            int page,
            int size) {

        if (restaurantId == null || restaurantId <= 0) {
            throw new IllegalArgumentException("El restaurante no es válido");
        }

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Los parámetros de paginación no son válidos");
        }

        return dishRepositoryPort.findByRestaurant(
                restaurantId,
                categoryId,
                page,
                size
        );
    }


}