
package com.pragma.plazoleta.infrastructure.output.persistence.mapper;

import com.pragma.plazoleta.domain.model.RestaurantEmployee;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantEmployeeEntityMapper {

    RestaurantEmployeeEntity toEntity(RestaurantEmployee restaurantEmployee);

    RestaurantEmployee toDomain(RestaurantEmployeeEntity entity);
}
