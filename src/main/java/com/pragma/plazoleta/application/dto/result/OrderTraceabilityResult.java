package com.pragma.plazoleta.application.dto.result;

import java.time.LocalDateTime;

public record OrderTraceabilityResult(
        String id,
        Long orderId,
        Long clientId,
        String clientEmail,
        LocalDateTime date,
        String previousStatus,
        String newStatus,
        Long employeeId,
        String employeeEmail
) {}