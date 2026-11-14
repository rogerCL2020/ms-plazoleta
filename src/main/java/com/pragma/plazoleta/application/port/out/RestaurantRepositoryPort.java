
package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.Restaurant;

import java.util.Optional;

public interface RestaurantRepositoryPort {

    boolean existsByNit(String nit);

    Restaurant save(Restaurant restaurant);

}
