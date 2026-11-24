
package com.pragma.plazoleta.application.event;

import com.pragma.plazoleta.application.port.out.OrderTraceabilityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderStatusChangedEventListener {

    private final OrderTraceabilityPort orderTraceabilityPort;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderStatusChangedEvent event) {
        orderTraceabilityPort.send(event);
    }
}
