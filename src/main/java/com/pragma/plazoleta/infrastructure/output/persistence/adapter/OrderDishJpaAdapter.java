
package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.OrderDishRepositoryPort;
import com.pragma.plazoleta.domain.model.OrderDish;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderDishEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.mapper.OrderDishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.OrderDishJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDishJpaAdapter implements OrderDishRepositoryPort {

    private final OrderDishJpaRepository orderDishJpaRepository;
    private final OrderDishEntityMapper orderDishEntityMapper;

    @Override
    public List<OrderDish> saveAll(List<OrderDish> orderDishes) {

        List<OrderDishEntity> entities = orderDishes.stream()
                .map(orderDishEntityMapper::toEntity)
                .toList();

        return orderDishJpaRepository.saveAll(entities)
                .stream()
                .map(orderDishEntityMapper::toDomain)
                .toList();
    }
}