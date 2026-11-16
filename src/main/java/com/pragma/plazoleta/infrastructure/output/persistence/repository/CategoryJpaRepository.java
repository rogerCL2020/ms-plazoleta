
package com.pragma.plazoleta.infrastructure.output.persistence.repository;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
}
