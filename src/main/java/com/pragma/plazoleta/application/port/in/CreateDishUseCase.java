package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Dish;

public interface CreateDishUseCase {

    Dish create(Dish dish, Long ownerId);
}