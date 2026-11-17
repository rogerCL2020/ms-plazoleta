package com.pragma.plazoleta.infrastructure.output.persistence.repository;


import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderDishEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderDishId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDishJpaRepository
        extends JpaRepository<OrderDishEntity, OrderDishId> {
}