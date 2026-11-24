
package com.pragma.plazoleta.application.event;

import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderStatusChangedEvent(
        Long orderId,
        Long clientId,
        LocalDateTime date,
        OrderStatus previousStatus,
        OrderStatus newStatus,
        Long employeeId
) {
}
