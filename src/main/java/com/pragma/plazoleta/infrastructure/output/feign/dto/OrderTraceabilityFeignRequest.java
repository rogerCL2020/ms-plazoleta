
package com.pragma.plazoleta.infrastructure.output.feign.dto;

import java.time.LocalDateTime;

public record OrderTraceabilityFeignRequest(
        Long orderId,
        Long clientId,
        String clientEmail,
        LocalDateTime date,
        String previousStatus,
        String newStatus,
        Long employeeId,
        String employeeEmail
) {
}
