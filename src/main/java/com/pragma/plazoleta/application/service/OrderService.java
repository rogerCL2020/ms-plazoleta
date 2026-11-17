
package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.dto.response.OrderListResponse;
import com.pragma.plazoleta.application.port.in.CreateOrderUseCase;
import com.pragma.plazoleta.application.port.in.ListOrderUseCase;
import com.pragma.plazoleta.application.port.out.*;
import com.pragma.plazoleta.domain.exception.*;
import com.pragma.plazoleta.domain.model.*;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase, ListOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderDishRepositoryPort orderDishRepositoryPort;
    private final RestaurantRepositoryPort restaurantRepositoryPort;
    private final DishRepositoryPort dishRepositoryPort;
    private final RestaurantEmployeeRepositoryPort restaurantEmployeeRepositoryPort;

    @Override
    @Transactional
    public Order create(Long clientId, Long restaurantId, List<OrderDish> dishes) {

        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("El cliente no es válido");
        }

        if (restaurantId == null || restaurantId <= 0) {
            throw new IllegalArgumentException("El restaurante no es válido");
        }

        if (dishes == null || dishes.isEmpty()) {
            throw new InvalidOrderDishException();
        }

        // Validar cantidades y evitar platos duplicados
        Set<Long> dishIds = new HashSet<>();

        for (OrderDish orderDish : dishes) {
            if (orderDish == null
                    || orderDish.getDishId() == null
                    || orderDish.getQuantity() == null
                    || orderDish.getQuantity() <= 0
                    || !dishIds.add(orderDish.getDishId())) {
                throw new InvalidOrderDishException();
            }
        }

        // Validar restaurante
        Restaurant restaurant = restaurantRepositoryPort.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);

        // Validar pedidos activos del cliente
        if (orderRepositoryPort.existsActiveOrderByClientId(clientId)) {
            throw new ActiveOrderAlreadyExistsException();
        }

        // Validar platos del restaurante y disponibilidad
        for (OrderDish orderDish : dishes) {

            Dish dish = dishRepositoryPort.findById(orderDish.getDishId())
                    .orElseThrow(DishNotFoundException::new);

            if (!Objects.equals(dish.getRestaurantId(), restaurant.getId())
                    || !Boolean.TRUE.equals(dish.getActive())) {
                throw new InvalidOrderDishException();
            }
        }

        // Crear pedido
        Order order = new Order(
                null,
                clientId,
                LocalDateTime.now(),
                OrderStatus.PENDING,
                null,
                restaurantId
        );

        Order savedOrder = orderRepositoryPort.save(order);

        // Asociar platos al pedido
        dishes.forEach(dish -> dish.setOrderId(savedOrder.getId()));

        orderDishRepositoryPort.saveAll(dishes);

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<OrderListResponse> getOrdersByStatus(
            Long employeeId,
            Long restaurantId,
            OrderStatus status,
            int page,
            int size
    ) {

        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("El empleado no es válido");
        }

        if (restaurantId == null || restaurantId <= 0) {
            throw new IllegalArgumentException("El restaurante no es válido");
        }

        if (status == null) {
            throw new IllegalArgumentException("El estado del pedido es obligatorio");
        }

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Los parámetros de paginación no son válidos");
        }

        boolean assignedToRestaurant =
                restaurantEmployeeRepositoryPort
                        .existsByEmployeeIdAndRestaurantId(employeeId, restaurantId);

        if (!assignedToRestaurant) {
            throw new UnauthorizedRestaurantAccessException();
        }

        PageResult<Order> result = orderRepositoryPort.findByRestaurantIdAndStatus(
                restaurantId,
                status,
                page,
                size
        );

        List<OrderListResponse> orders = result.getContent()
                .stream()
                .map(order -> new OrderListResponse(
                        order.getId(),
                        order.getClientId(),
                        order.getDate(),
                        order.getStatus(),
                        order.getChefId(),
                        null
                ))
                .toList();

        return new PageResult<>(
                orders,
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
