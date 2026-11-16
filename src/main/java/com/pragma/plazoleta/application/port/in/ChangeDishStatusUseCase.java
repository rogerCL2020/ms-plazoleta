package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Dish;

public interface ChangeDishStatusUseCase {

    Dish changeStatus(Long dishId, Boolean active, Long ownerId);

}