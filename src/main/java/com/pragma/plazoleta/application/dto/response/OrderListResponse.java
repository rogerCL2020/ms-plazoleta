package com.pragma.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pragma.plazoleta.domain.model.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderListResponse(

        Long id,

        @JsonProperty("idCliente")
        Long clientId,

        @JsonProperty("fecha")
        LocalDateTime date,

        @JsonProperty("estado")
        OrderStatus status,

        @JsonProperty("idChef")
        Long chefId,

        @JsonProperty("nombreChef")
        String chefName

) {}