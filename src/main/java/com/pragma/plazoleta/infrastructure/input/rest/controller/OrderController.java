
package com.pragma.plazoleta.infrastructure.input.rest.controller;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.request.DeliverOrderRequest;
import com.pragma.plazoleta.application.dto.response.OrderListResponse;
import com.pragma.plazoleta.application.dto.response.OrderResponse;
import com.pragma.plazoleta.application.port.in.*;
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
    private final MarkOrderReadyUseCase markOrderReadyUseCase;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final MarkOrderDeliveredUseCase markOrderDeliveredUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<OrderResponse> create(
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

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(order));
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PageResult<OrderResponse>> getOrdersByStatus(
            @RequestParam Long restaurantId,
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        PageResult<Order> result = listOrderUseCase.getOrdersByStatus(
                user.userId(),
                restaurantId,
                status,
                page,
                size
        );

        List<OrderResponse> orders = result.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        PageResult<OrderResponse> response = new PageResult<>(
                orders,
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}/assign")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<OrderResponse> assignOrder(
            @PathVariable Long orderId) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Order order = assignOrderUseCase.assignOrder(
                user.userId(),
                orderId
        );

        return ResponseEntity.ok(toResponse(order));
    }

    @PostMapping("/{orderId}/ready")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<OrderResponse> markAsReady(
            @PathVariable Long orderId) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Order order = markOrderReadyUseCase.markAsReady(
                user.userId(),
                orderId
        );

        return ResponseEntity.ok(toResponse(order));
    }

    @PostMapping("/{orderId}/deliver")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<OrderResponse> markAsDelivered(
            @PathVariable Long orderId,
            @Valid @RequestBody DeliverOrderRequest request
    ) {
        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Order order = markOrderDeliveredUseCase.markAsDelivered(
                user.userId(),
                orderId,
                request.securityPin()
        );

        return ResponseEntity.ok(toResponse(order));
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable Long orderId
    ) {
        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Order order = cancelOrderUseCase.cancelOrder(
                user.userId(),
                orderId
        );

        return ResponseEntity.ok(toResponse(order));
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getClientId(),
                order.getRestaurantId(),
                order.getChefId(),
                order.getStatus(),
                order.getDate(),
                order.getPreparationStartedAt(),
                order.getDeliveredAt()
        );
    }
}
