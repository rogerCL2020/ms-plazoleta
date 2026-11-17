
package com.pragma.plazoleta.infrastructure.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos_platos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishEntity {

    @EmbeddedId
    private OrderDishId id;

    @Column(name = "cantidad", nullable = false)
    private Integer quantity;
}
