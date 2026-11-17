
package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.port.in.CreateOrderUseCase;
import com.pragma.plazoleta.application.port.out.DishRepositoryPort;
import com.pragma.plazoleta.application.port.out.OrderDishRepositoryPort;
import com.pragma.plazoleta.application.port.out.OrderRepositoryPort;
import com.pragma.plazoleta.application.port.out.RestaurantRepositoryPort;
import com.pragma.plazoleta.domain.exception.ActiveOrderAlreadyExistsException;
import com.pragma.plazoleta.domain.exception.DishNotFoundException;
import com.pragma.plazoleta.domain.exception.InvalidOrderDishException;
import com.pragma.plazoleta.domain.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderDish;
import com.pragma.plazoleta.domain.model.Restaurant;
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
public class OrderService implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderDishRepositoryPort orderDishRepositoryPort;
    private final RestaurantRepositoryPort restaurantRepositoryPort;
    private final DishRepositoryPort dishRepositoryPort;

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
}
