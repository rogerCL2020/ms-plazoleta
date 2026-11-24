package com.pragma.plazoleta.application.port.in;


import com.pragma.plazoleta.application.dto.result.OrderTraceabilityResult;

import java.util.List;

public interface OrderTraceabilityUseCase {

    List<OrderTraceabilityResult> getByOrderId(
            Long orderId,
            Long clientId
    );

    List<OrderTraceabilityResult> getByClientId(Long clientId);
}