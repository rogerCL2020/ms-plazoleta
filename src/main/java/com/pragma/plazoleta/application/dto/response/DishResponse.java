package com.pragma.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DishResponse {

    private Long id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("idCategoria")
    private Long categoryId;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("precio")
    private BigDecimal price;

    @JsonProperty("idRestaurante")
    private Long restaurantId;

    @JsonProperty("urlImagen")
    private String imageUrl;

    @JsonProperty("activo")
    private Boolean active;
}