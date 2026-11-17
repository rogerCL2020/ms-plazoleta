package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderDish;

import java.util.List;

public interface CreateOrderUseCase {

    Order create(Long clientId, Long restaurantId, List<OrderDish> dishes);
}