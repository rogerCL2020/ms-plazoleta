
package com.pragma.plazoleta.infrastructure.input.rest.controller;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.OrderListResponse;
import com.pragma.plazoleta.application.port.in.AssignOrderUseCase;
import com.pragma.plazoleta.application.port.in.CreateOrderUseCase;
import com.pragma.plazoleta.application.port.in.ListOrderUseCase;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderDish;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;
import com.pragma.plazoleta.infrastructure.security.AuthenticatedUser;
import com.pragma.plazoleta.infrastructure.security.AuthenticatedUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final ListOrderUseCase listOrderUseCase;
    private final AssignOrderUseCase assignOrderUseCase;
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

    @GetMapping("/employee")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PageResult<OrderListResponse>> getOrdersByStatus(
            @RequestParam Long restaurantId,
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        PageResult<OrderListResponse> result = listOrderUseCase.getOrdersByStatus(
                user.userId(),
                restaurantId,
                status,
                page,
                size
        );

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{orderId}/assign")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<Order> assignOrder(
            @PathVariable Long orderId) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Order order = assignOrderUseCase.assignOrder(
                user.userId(),
                orderId
        );

        return ResponseEntity.ok(order);
    }
}
