
package com.pragma.plazoleta.infrastructure.output.persistence.adapter;

import com.pragma.plazoleta.application.port.out.CategoryRepositoryPort;
import com.pragma.plazoleta.infrastructure.output.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryJpaAdapter implements CategoryRepositoryPort {

    private final CategoryJpaRepository categoryRepository;

    @Override
    public boolean existsById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
