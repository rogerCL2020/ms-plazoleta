package com.pragma.plazoleta.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DeliverOrderRequest(

        @NotBlank(message = "El PIN es obligatorio")
        @Pattern(
                regexp = "\\d{6}",
                message = "El PIN debe contener 6 dígitos"
        )
        String securityPin

) {
}