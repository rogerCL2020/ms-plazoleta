package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.dto.result.OrderTraceabilityResult;
import com.pragma.plazoleta.application.port.in.OrderTraceabilityUseCase;
import com.pragma.plazoleta.application.port.out.OrderRepositoryPort;
import com.pragma.plazoleta.application.port.out.OrderTraceabilityPort;
import com.pragma.plazoleta.domain.exception.OrderNotFoundException;
import com.pragma.plazoleta.domain.exception.UnauthorizedOrderAccessException;
import com.pragma.plazoleta.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderTraceabilityService implements OrderTraceabilityUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderTraceabilityPort orderTraceabilityPort;

    @Override
    public List<OrderTraceabilityResult> getByOrderId(
            Long orderId,
            Long clientId
    ) {

        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!Objects.equals(order.getClientId(), clientId)) {
            throw new UnauthorizedOrderAccessException();
        }

        return orderTraceabilityPort.findByOrderIdAndClientId(
                orderId,
                clientId
        );
    }

    @Override
    public List<OrderTraceabilityResult> getByClientId(Long clientId) {

        return orderTraceabilityPort.findByClientId(clientId);
    }
}