
package com.pragma.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantListResponse {

    @JsonProperty("nombre")
    private final String name;

    @JsonProperty("urlLogo")
    private final String logoUrl;
}
