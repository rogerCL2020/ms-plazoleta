
package com.pragma.plazoleta.infrastructure.output.persistence.repository;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DishJpaRepository extends JpaRepository<DishEntity, Long> {

    @Query("""
            SELECT d
            FROM DishEntity d
            WHERE d.restaurantId = :restaurantId
            AND (:categoryId IS NULL OR d.categoryId = :categoryId)
            """)
    Page<DishEntity> findByRestaurant(
            @Param("restaurantId") Long restaurantId,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    boolean existsByNameAndCategoryIdAndRestaurantId(
            String name,
            Long categoryId,
            Long restaurantId
    );
}
