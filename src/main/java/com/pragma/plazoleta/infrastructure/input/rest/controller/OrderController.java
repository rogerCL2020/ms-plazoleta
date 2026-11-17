
package com.pragma.plazoleta.infrastructure.input.rest.controller;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.port.in.CreateOrderUseCase;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderDish;
import com.pragma.plazoleta.infrastructure.security.AuthenticatedUser;
import com.pragma.plazoleta.infrastructure.security.AuthenticatedUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Order> create(
            @Valid @RequestBody CreateOrderRequest request) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        List<OrderDish> dishes = request.dishes().stream()
                .map(dish -> new OrderDish(
                        null,
                        dish.dishId(),
                        dish.quantity()
                ))
                .toList();

        Order order = createOrderUseCase.create(
                user.userId(),
                request.restaurantId(),
                dishes
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
