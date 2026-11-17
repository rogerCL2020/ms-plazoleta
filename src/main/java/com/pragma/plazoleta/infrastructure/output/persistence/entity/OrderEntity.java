
package com.pragma.plazoleta.infrastructure.output.persistence.entity;

import com.pragma.plazoleta.domain.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    private Long clientId;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private OrderStatus status;

    @Column(name = "id_chef")
    private Long chefId;

    @Column(name = "id_restaurante", nullable = false)
    private Long restaurantId;
}
