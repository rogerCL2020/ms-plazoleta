package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.application.dto.response.OrderListResponse;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;

public interface ListOrderUseCase {

    PageResult<Order> getOrdersByStatus(
            Long employeeId,
            Long restaurantId,
            OrderStatus status,
            int page,
            int size
    );
}
