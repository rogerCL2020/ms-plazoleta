package com.pragma.plazoleta.infrastructure.output.persistence.mapper;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity entity);
}