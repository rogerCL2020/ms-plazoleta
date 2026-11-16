
package com.pragma.plazoleta.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "platos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "id_categoria", nullable = false)
    private Long categoryId;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "precio", nullable = false, precision = 10, scale = 0)
    private BigDecimal price;

    @Column(name = "id_restaurante", nullable = false)
    private Long restaurantId;

    @Column(name = "url_imagen", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "activo", nullable = false)
    private Boolean active;
}
