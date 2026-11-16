
package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.PageResult;
import com.pragma.plazoleta.domain.model.Restaurant;

public interface ListRestaurantsUseCase {

    PageResult<Restaurant> list(int page, int size);
}
