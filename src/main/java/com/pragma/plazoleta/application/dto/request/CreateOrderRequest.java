
package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateOrderRequest(

        @JsonProperty("idRestaurante")
        @NotNull
        @Positive
        Long restaurantId,

        @JsonProperty("platos")
        @NotEmpty
        List<@Valid OrderDishRequest> dishes

) {

    public record OrderDishRequest(

            @JsonProperty("idPlato")
            @NotNull
            @Positive
            Long dishId,

            @JsonProperty("cantidad")
            @NotNull
            @Positive
            Integer quantity

    ) {}
}
