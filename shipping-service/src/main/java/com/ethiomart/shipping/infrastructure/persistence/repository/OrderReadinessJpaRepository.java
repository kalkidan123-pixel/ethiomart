package com.ethiomart.shipping.infrastructure.persistence.repository;

import com.ethiomart.shipping.infrastructure.persistence.entity.OrderReadinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReadinessJpaRepository extends JpaRepository<OrderReadinessEntity, String> {
}
