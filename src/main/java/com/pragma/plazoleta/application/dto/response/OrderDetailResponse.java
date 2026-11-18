
package com.pragma.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse(

        Long id,

        @JsonProperty("idCliente")
        Long clientId,

        @JsonProperty("fecha")
        LocalDateTime date,

        @JsonProperty("estado")
        OrderStatus status,

        @JsonProperty("restaurante")
        RestaurantResponse restaurant,

        @JsonProperty("platos")
        List<OrderDishResponse> dishes

) {

    public record RestaurantResponse(
            Long id,
            String nombre
    ) {}

    public record OrderDishResponse(

            @JsonProperty("idPlato")
            Long dishId,

            String nombre,

            Integer cantidad

    ) {}
}
