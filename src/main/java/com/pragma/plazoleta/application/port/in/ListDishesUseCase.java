
package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.PageResult;

public interface ListDishesUseCase {

    PageResult<Dish> listByRestaurant(
            Long restaurantId,
            Long categoryId,
            int page,
            int size
    );
}
