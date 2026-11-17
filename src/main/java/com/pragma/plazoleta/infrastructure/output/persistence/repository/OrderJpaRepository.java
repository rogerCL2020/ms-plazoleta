package com.pragma.plazoleta.infrastructure.output.persistence.repository;

import com.pragma.plazoleta.domain.model.enums.OrderStatus;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByClientIdAndStatusIn(
            Long clientId,
            Collection<OrderStatus> statuses
    );
}