package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Order;

public interface MarkOrderDeliveredUseCase {

    Order markAsDelivered(Long employeeId, Long orderId, String securityPin);

}