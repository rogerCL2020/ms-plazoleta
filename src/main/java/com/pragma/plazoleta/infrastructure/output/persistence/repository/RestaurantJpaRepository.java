package com.pragma.plazoleta.infrastructure.output.persistence.repository;

import com.pragma.plazoleta.infrastructure.output.persistence.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantJpaRepository
        extends JpaRepository<RestaurantEntity, Long> {

    boolean existsByNit(String nit);

    Optional<RestaurantEntity> findByOwnerId(Long ownerId);

}