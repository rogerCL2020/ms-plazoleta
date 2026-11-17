package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.Order;

public interface OrderRepositoryPort {

    Order save(Order order);

    boolean existsActiveOrderByClientId(Long clientId);
}