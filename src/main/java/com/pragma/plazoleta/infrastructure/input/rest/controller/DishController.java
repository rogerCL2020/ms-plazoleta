
package com.pragma.plazoleta.infrastructure.input.rest.controller;

import com.pragma.plazoleta.application.dto.request.ChangeDishStatusRequest;
import com.pragma.plazoleta.application.dto.request.CreateDishRequest;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.response.DishResponse;
import com.pragma.plazoleta.application.port.in.ChangeDishStatusUseCase;
import com.pragma.plazoleta.application.port.in.CreateDishUseCase;
import com.pragma.plazoleta.application.port.in.UpdateDishUseCase;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.PageResult;

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
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishController {

    private final CreateDishUseCase createDishUseCase;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final UpdateDishUseCase updateDishUseCase;
    private final ChangeDishStatusUseCase changeDishStatusUseCase;

    @PostMapping
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<DishResponse> create(
            @Valid @RequestBody CreateDishRequest request
    ) {
        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Dish dish = toDomain(request);

        Dish created = createDishUseCase.create(dish, user.userId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<DishResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishRequest request) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Dish updated = updateDishUseCase.update(
                id,
                request.getPrice(),
                request.getDescription(),
                user.userId()
        );

        return ResponseEntity.ok(toResponse(updated));
    }


    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<DishResponse> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeDishStatusRequest request) {

        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        Dish updated = changeDishStatusUseCase.changeStatus(
                id,
                request.getActive(),
                user.userId()
        );

        return ResponseEntity.ok(toResponse(updated));
    }


    private Dish toDomain(CreateDishRequest request) {
        return new Dish(
                null,
                request.getName(),
                request.getCategoryId(),
                request.getDescription(),
                request.getPrice(),
                request.getRestaurantId(),
                request.getImageUrl(),
                true
        );
    }

    private DishResponse toResponse(Dish dish) {
        DishResponse response = new DishResponse();
        response.setId(dish.getId());
        response.setName(dish.getName());
        response.setCategoryId(dish.getCategoryId());
        response.setDescription(dish.getDescription());
        response.setPrice(dish.getPrice());
        response.setRestaurantId(dish.getRestaurantId());
        response.setImageUrl(dish.getImageUrl());
        response.setActive(dish.getActive());

        return response;
    }
}
