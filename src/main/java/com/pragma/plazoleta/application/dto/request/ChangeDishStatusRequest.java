package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeDishStatusRequest {

    @JsonProperty("activo")
    @NotNull(message = "El estado del plato es obligatorio")
    private Boolean active;
}