
package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class
CreateRestaurantRequest {

    @JsonProperty("nombre")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    @JsonProperty("direccion")
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String address;

    @JsonProperty("idPropietario")
    @NotNull(message = "El propietario es obligatorio")
    private Long ownerId;

    @JsonProperty("telefono")
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(
            regexp = "\\+?[0-9]{1,12}",
            message = "El teléfono debe contener máximo 13 caracteres numéricos, incluyendo el signo + opcional"
    )
    private String phone;

    @JsonProperty("urlLogo")
    @Size(max = 500, message = "La URL del logo no puede superar los 500 caracteres")
    private String logoUrl;

    @JsonProperty("nit")
    @NotBlank(message = "El NIT es obligatorio")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "El NIT debe contener únicamente números"
    )
    @Size(max = 20, message = "El NIT no puede superar los 20 caracteres")
    private String nit;
}
