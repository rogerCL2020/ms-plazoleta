
package com.pragma.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DishListResponse {

    private Long id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("precio")
    private BigDecimal price;

    @JsonProperty("urlImagen")
    private String imageUrl;
}
