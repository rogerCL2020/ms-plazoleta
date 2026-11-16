
package com.pragma.plazoleta.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "restaurante_empleados",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_restaurante_empleado",
                        columnNames = {"id_empleado"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_restaurante", nullable = false)
    private Long restaurantId;

    @Column(name = "id_empleado", nullable = false)
    private Long employeeId;
}
