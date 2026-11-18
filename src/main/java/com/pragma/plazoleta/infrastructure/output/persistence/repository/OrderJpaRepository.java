package com.pragma.plazoleta.infrastructure.output.persistence.repository;

import com.pragma.plazoleta.domain.model.enums.OrderStatus;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByClientIdAndStatusIn(
            Long clientId,
            Collection<OrderStatus> statuses
    );

    Page<OrderEntity> findByRestaurantIdAndStatus(
            Long restaurantId,
            OrderStatus status,
            Pageable pageable
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE OrderEntity o
            SET o.chefId = :employeeId,
                o.status = :newStatus,
                o.preparationStartedAt = :startedAt
            WHERE o.id = :orderId
              AND o.status = :expectedStatus
              AND o.chefId IS NULL
            """)
    int assignIfAvailable(
            @Param("orderId") Long orderId,
            @Param("employeeId") Long employeeId,
            @Param("expectedStatus") OrderStatus expectedStatus,
            @Param("newStatus") OrderStatus newStatus,
            @Param("startedAt") LocalDateTime startedAt
    );


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE OrderEntity o
        SET o.status = :newStatus,
            o.securityPin = :securityPin
        WHERE o.id = :orderId
          AND o.status = :expectedStatus
          AND o.chefId = :employeeId
        """)
    int markAsReady(
            @Param("orderId") Long orderId,
            @Param("employeeId") Long employeeId,
            @Param("securityPin") String securityPin,
            @Param("expectedStatus") OrderStatus expectedStatus,
            @Param("newStatus") OrderStatus newStatus
    );

}