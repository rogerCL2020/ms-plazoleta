package com.pragma.plazoleta.application.dto.response;

import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long clientId,
        Long restaurantId,
        Long chefId,
        OrderStatus status,
        LocalDateTime date,
        LocalDateTime preparationStartedAt,
        LocalDateTime deliveredAt
) {
}