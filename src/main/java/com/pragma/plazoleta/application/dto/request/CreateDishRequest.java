package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateDishRequest {

    @JsonProperty("nombre")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    @JsonProperty("idCategoria")
    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

    @JsonProperty("descripcion")
    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @JsonProperty("precio")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0", inclusive = false, message = "El precio debe ser mayor que cero")
    @Digits(integer = 10, fraction = 0, message = "El precio debe ser un número entero")
    private BigDecimal price;

    @JsonProperty("idRestaurante")
    @NotNull(message = "El restaurante es obligatorio")
    private Long restaurantId;

    @JsonProperty("urlImagen")
    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres")
    private String imageUrl;
}