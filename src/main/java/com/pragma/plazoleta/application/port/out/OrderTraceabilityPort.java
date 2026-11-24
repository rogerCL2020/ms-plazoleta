
package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.application.dto.result.OrderTraceabilityResult;
import com.pragma.plazoleta.application.event.OrderStatusChangedEvent;

import java.util.List;

public interface OrderTraceabilityPort {

    void send(OrderStatusChangedEvent event);

    List<OrderTraceabilityResult> findByOrderIdAndClientId(
            Long orderId,
            Long clientId
    );

    List<OrderTraceabilityResult> findByClientId(Long clientId);

}
