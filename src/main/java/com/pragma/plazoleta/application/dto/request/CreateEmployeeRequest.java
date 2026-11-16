
package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record CreateEmployeeRequest(

        @JsonProperty("nombre")
        @NotBlank
        @Size(max = 100)
        String firstName,

        @JsonProperty("apellido")
        @NotBlank
        @Size(max = 100)
        String lastName,

        @JsonProperty("numeroDocumento")
        @NotBlank
        @Pattern(regexp = "\\d+")
        String documentNumber,

        @JsonProperty("celular")
        @NotBlank
        @Pattern(regexp = "^\\+?[0-9]{1,12}$")
        String phone,

        @JsonProperty("correo")
        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @JsonProperty("clave")
        @NotBlank
        @Size(min = 8, max = 72)
        String password,

        @JsonProperty("idRestaurante")
        @NotNull
        @Positive
        Long restaurantId


) {
}
