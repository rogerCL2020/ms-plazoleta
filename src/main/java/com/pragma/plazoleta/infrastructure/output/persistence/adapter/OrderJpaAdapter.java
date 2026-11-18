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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .map(orderEntityMapper::toDomain);
    }

    @Override
    public int assignIfAvailable(
            Long orderId,
            Long employeeId,
            OrderStatus expectedStatus,
            OrderStatus newStatus,
            LocalDateTime startedAt
    ) {
        return orderJpaRepository.assignIfAvailable(
                orderId,
                employeeId,
                expectedStatus,
                newStatus,
                startedAt
        );
    }


    @Override
    public int markAsReady(
            Long orderId,
            Long employeeId,
            String securityPin,
            OrderStatus expectedStatus,
            OrderStatus newStatus
    ) {
        return orderJpaRepository.markAsReady(
                orderId,
                employeeId,
                securityPin,
                expectedStatus,
                newStatus
        );
    }


}