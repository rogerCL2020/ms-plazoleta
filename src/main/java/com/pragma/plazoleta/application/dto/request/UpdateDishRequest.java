package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateDishRequest {

    @JsonProperty("precio")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0", inclusive = false, message = "El precio debe ser mayor que cero")
    @Digits(integer = 10, fraction = 0, message = "El precio debe ser un número entero")
    private BigDecimal price;

    @JsonProperty("descripcion")
    @NotBlank(message = "La descripción es obligatoria")
    private String description;
}