
package com.pragma.plazoleta.infrastructure.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishId implements Serializable {

    @Column(name = "id_pedido")
    private Long orderId;

    @Column(name = "id_plato")
    private Long dishId;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OrderDishId that)) return false;
        return Objects.equals(orderId, that.orderId)
                && Objects.equals(dishId, that.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishId);
    }
}
