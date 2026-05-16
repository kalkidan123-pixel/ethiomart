package com.ethiomart.inventory.infrastructure.persistence.repository;

import com.ethiomart.inventory.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, String> {
}
