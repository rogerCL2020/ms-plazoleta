
package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.PageResult;

import java.util.Optional;

public interface DishRepositoryPort {

    Dish save(Dish dish);



    boolean existsByNameAndCategoryIdAndRestaurantId(String name, Long categoryId, Long restaurantId);
}
