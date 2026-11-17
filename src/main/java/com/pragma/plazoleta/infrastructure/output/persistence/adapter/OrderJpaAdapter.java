package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.OrderRepositoryPort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.OrderEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Order save(Order order) {
        OrderEntity entity = orderEntityMapper.toEntity(order);
        OrderEntity savedEntity = orderJpaRepository.save(entity);
        return orderEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsActiveOrderByClientId(Long clientId) {
        List<OrderStatus> activeStatuses = List.of(
                OrderStatus.PENDING,
                OrderStatus.IN_PREPARATION,
                OrderStatus.READY
        );

        return orderJpaRepository.existsByClientIdAndStatusIn(
                clientId,
                activeStatuses
        );
    }
}