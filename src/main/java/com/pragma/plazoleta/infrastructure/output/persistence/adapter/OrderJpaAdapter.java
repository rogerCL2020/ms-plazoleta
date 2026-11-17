package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.OrderRepositoryPort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.OrderEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public PageResult<Order> findByRestaurantIdAndStatus(
            Long restaurantId,
            OrderStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> result = orderJpaRepository.findByRestaurantIdAndStatus(
                restaurantId,
                status,
                pageable
        );

        return new PageResult<>(
                result.getContent().stream()
                        .map(orderEntityMapper::toDomain)
                        .toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

}