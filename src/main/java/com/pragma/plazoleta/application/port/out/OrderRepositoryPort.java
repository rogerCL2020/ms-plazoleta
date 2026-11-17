package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import java.util.Optional;

public interface OrderRepositoryPort {

    Order save(Order order);

    boolean existsActiveOrderByClientId(Long clientId);

    PageResult<Order> findByRestaurantIdAndStatus(
            Long restaurantId,
            OrderStatus status,
            int page,
            int size
    );

    Optional<Order> findById(Long orderId);

}