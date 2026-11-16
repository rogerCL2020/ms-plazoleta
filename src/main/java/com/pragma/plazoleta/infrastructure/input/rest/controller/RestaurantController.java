
package com.pragma.plazoleta.infrastructure.input.rest.controller;


import com.pragma.plazoleta.application.dto.request.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.response.RestaurantListResponse;
import com.pragma.plazoleta.application.dto.response.RestaurantResponse;
import com.pragma.plazoleta.application.port.in.CreateRestaurantUseCase;
import com.pragma.plazoleta.application.port.in.ListRestaurantsUseCase;
import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.Restaurant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final ListRestaurantsUseCase listRestaurantsUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponse> create(
            @Valid @RequestBody CreateRestaurantRequest request
    ) {
        Restaurant restaurant = toDomain(request);

        Restaurant created = createRestaurantUseCase.create(restaurant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(created));
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PageResult<RestaurantListResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResult<Restaurant> result = listRestaurantsUseCase.list(page, size);

        List<RestaurantListResponse> content = result.getContent()
                .stream()
                .map(this::toListResponse)
                .toList();

        PageResult<RestaurantListResponse> response = new PageResult<>(
                content,
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    private RestaurantListResponse toListResponse(Restaurant restaurant) {
        return new RestaurantListResponse(
                restaurant.getName(),
                restaurant.getLogoUrl()
        );
    }

    private Restaurant toDomain(CreateRestaurantRequest request) {
        return new Restaurant(
                null,
                request.getName(),
                request.getAddress(),
                request.getOwnerId(),
                request.getPhone(),
                request.getLogoUrl(),
                request.getNit()
        );
    }

    private RestaurantResponse toResponse(Restaurant restaurant) {
        RestaurantResponse response = new RestaurantResponse();
        response.setId(restaurant.getId());
        response.setName(restaurant.getName());
        response.setAddress(restaurant.getAddress());
        response.setOwnerId(restaurant.getOwnerId());
        response.setPhone(restaurant.getPhone());
        response.setLogoUrl(restaurant.getLogoUrl());
        response.setNit(restaurant.getNit());
        return response;
    }
}
