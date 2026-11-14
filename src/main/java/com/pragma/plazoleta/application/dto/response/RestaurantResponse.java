
package com.pragma.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse {

    private Long id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("direccion")
    private String address;

    @JsonProperty("idPropietario")
    private Long ownerId;

    @JsonProperty("telefono")
    private String phone;

    @JsonProperty("urlLogo")
    private String logoUrl;

    private String nit;
}
