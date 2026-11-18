package com.pragma.plazoleta.application.event;

public record OrderReadyEvent(
        Long orderId,
        Long clientId,
        String securityPin
) {
}