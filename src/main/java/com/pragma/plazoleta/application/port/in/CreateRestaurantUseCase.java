package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Restaurant;

public interface CreateRestaurantUseCase {

    Restaurant create(Restaurant restaurant);
}