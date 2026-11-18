package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import java.time.LocalDateTime;
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

    int assignIfAvailable(
            Long orderId,
            Long employeeId,
            OrderStatus expectedStatus,
            OrderStatus newStatus,
            LocalDateTime startedAt
    );


    int markAsReady(
            Long orderId,
            Long employeeId,
            String securityPin,
            OrderStatus expectedStatus,
            OrderStatus newStatus
    );

    int markAsDelivered(
            Long orderId,
            String securityPin,
            OrderStatus expectedStatus,
            OrderStatus newStatus,
            LocalDateTime deliveredAt
    );

}