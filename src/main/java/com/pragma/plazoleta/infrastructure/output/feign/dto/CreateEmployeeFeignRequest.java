
package com.pragma.plazoleta.infrastructure.output.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateEmployeeFeignRequest(
        @JsonProperty("nombre") String firstName,
        @JsonProperty("apellido") String lastName,
        @JsonProperty("numeroDocumento") String documentNumber,
        @JsonProperty("celular") String phone,
        @JsonProperty("correo") String email,
        @JsonProperty("clave") String password
) {
}
