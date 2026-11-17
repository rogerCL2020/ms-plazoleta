package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.OrderDish;

import java.util.List;

public interface OrderDishRepositoryPort {

    List<OrderDish> saveAll(List<OrderDish> orderDishes);
}