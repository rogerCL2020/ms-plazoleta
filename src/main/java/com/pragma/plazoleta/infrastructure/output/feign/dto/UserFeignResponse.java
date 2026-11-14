
package com.pragma.plazoleta.infrastructure.output.feign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserFeignResponse(
        Long id,
        @JsonProperty("nombre") String firstName,
        @JsonProperty("apellido") String lastName,
        @JsonProperty("correo") String email,
        @JsonProperty("idRol") Long roleId,
        @JsonProperty("nombreRol") String roleName
) {
}
