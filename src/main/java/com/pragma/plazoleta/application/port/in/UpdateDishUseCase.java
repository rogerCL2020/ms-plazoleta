package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Dish;

import java.math.BigDecimal;

public interface UpdateDishUseCase {

    Dish update(Long dishId, BigDecimal price, String description, Long ownerId);

}