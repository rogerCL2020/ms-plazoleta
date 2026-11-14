
package com.pragma.plazoleta.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "restaurantes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_restaurantes_nit",
                        columnNames = "nit"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "direccion", nullable = false, length = 255)
    private String address;

    @Column(name = "id_propietario", nullable = false)
    private Long ownerId;

    @Column(name = "telefono", nullable = false, length = 13)
    private String phone;

    @Column(name = "url_logo", length = 500)
    private String logoUrl;

    @Column(nullable = false, length = 20)
    private String nit;
}
