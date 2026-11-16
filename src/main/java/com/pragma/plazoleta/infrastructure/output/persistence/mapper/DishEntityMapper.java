
package com.pragma.plazoleta.infrastructure.output.persistence.mapper;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.infrastructure.output.persistence.entity.DishEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DishEntityMapper {

    DishEntity toEntity(Dish dish);

    Dish toDomain(DishEntity dishEntity);
}
