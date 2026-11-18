package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Order;

public interface CancelOrderUseCase {

    Order cancelOrder(Long clientId, Long orderId);
}