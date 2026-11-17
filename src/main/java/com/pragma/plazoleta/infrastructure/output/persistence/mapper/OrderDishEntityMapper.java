package com.pragma.plazoleta.infrastructure.output.persistence.mapper;

import com.pragma.plazoleta.domain.model.OrderDish;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderDishEntity;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.OrderDishId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDishEntityMapper {

    @Mapping(target = "id", expression = "java(toId(orderDish))")
    OrderDishEntity toEntity(OrderDish orderDish);

    @Mapping(target = "orderId", source = "id.orderId")
    @Mapping(target = "dishId", source = "id.dishId")
    OrderDish toDomain(OrderDishEntity entity);

    default OrderDishId toId(OrderDish orderDish) {
        return new OrderDishId(
                orderDish.getOrderId(),
                orderDish.getDishId()
        );
    }
}