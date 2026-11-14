package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.port.in.CreateRestaurantUseCase;
import com.pragma.plazoleta.application.port.out.RestaurantRepositoryPort;
import com.pragma.plazoleta.application.port.out.UserServicePort;
import com.pragma.plazoleta.domain.exception.InvalidOwnerException;
import com.pragma.plazoleta.domain.exception.RestaurantAlreadyExistsException;
import com.pragma.plazoleta.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService implements CreateRestaurantUseCase {

    private final RestaurantRepositoryPort restaurantRepositoryPort;
    private final UserServicePort userServicePort;

    @Override
    public Restaurant create(Restaurant restaurant) {

        if (!userServicePort.existsOwnerById(restaurant.getOwnerId())) {
            throw new InvalidOwnerException();
        }

        if (restaurantRepositoryPort.existsByNit(restaurant.getNit())) {
            throw new RestaurantAlreadyExistsException();
        }

        return restaurantRepositoryPort.save(restaurant);
    }


}