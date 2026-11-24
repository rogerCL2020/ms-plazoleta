
package com.pragma.plazoleta.infrastructure.output.feign.adapter;

import com.pragma.plazoleta.application.dto.result.OrderTraceabilityResult;
import com.pragma.plazoleta.application.event.OrderStatusChangedEvent;
import com.pragma.plazoleta.application.port.out.OrderTraceabilityPort;
import com.pragma.plazoleta.infrastructure.output.feign.client.TraceabilityFeignClient;
import com.pragma.plazoleta.infrastructure.output.feign.client.UserFeignClient;
import com.pragma.plazoleta.infrastructure.output.feign.dto.OrderTraceabilityFeignRequest;
import com.pragma.plazoleta.infrastructure.output.feign.dto.OrderTraceabilityFeignResponse;
import com.pragma.plazoleta.infrastructure.output.feign.dto.UserFeignResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderTraceabilityAdapter implements OrderTraceabilityPort {

    private final TraceabilityFeignClient traceabilityFeignClient;
    private final UserFeignClient userFeignClient;

    @Override
    public void send(OrderStatusChangedEvent event) {

        UserFeignResponse client =
                userFeignClient.getUserById(event.clientId());

        UserFeignResponse employee = event.employeeId() != null
                ? userFeignClient.getUserById(event.employeeId())
                : null;

        OrderTraceabilityFeignRequest request =
                new OrderTraceabilityFeignRequest(
                        event.orderId(),
                        event.clientId(),
                        client.email(),
                        event.date(),
                        event.previousStatus() == null
                                ? null
                                : event.previousStatus().name(),
                        event.newStatus().name(),
                        event.employeeId(),
                        employee == null ? null : employee.email()
                );

        traceabilityFeignClient.save(request);
    }

    @Override
    public List<OrderTraceabilityResult> findByOrderIdAndClientId(
            Long orderId,
            Long clientId
    ) {
        return traceabilityFeignClient
                .getByOrderIdAndClientId(orderId, clientId)
                .stream()
                .map(this::toResult)
                .toList();
    }

    @Override
    public List<OrderTraceabilityResult> findByClientId(Long clientId) {
        return traceabilityFeignClient
                .getByClientId(clientId)
                .stream()
                .map(this::toResult)
                .toList();
    }

    private OrderTraceabilityResult toResult(
            OrderTraceabilityFeignResponse response
    ) {
        return new OrderTraceabilityResult(
                response.id(),
                response.orderId(),
                response.clientId(),
                response.clientEmail(),
                response.date(),
                response.previousStatus(),
                response.newStatus(),
                response.employeeId(),
                response.employeeEmail()
        );
    }
}
