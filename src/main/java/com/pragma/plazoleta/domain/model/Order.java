
package com.pragma.plazoleta.domain.model;

import com.pragma.plazoleta.domain.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private OrderStatus status;
    private Long chefId;
    private Long restaurantId;
    private String securityPin;
    private LocalDateTime preparationStartedAt;
    private LocalDateTime deliveredAt;
}
