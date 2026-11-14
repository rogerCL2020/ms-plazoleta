
package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private BigDecimal price;
    private Long restaurantId;
    private String imageUrl;
    private Boolean active;
}
