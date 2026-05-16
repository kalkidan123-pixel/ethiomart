package com.ethiomart.shipping.infrastructure.persistence.repository;

import com.ethiomart.shipping.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentJpaRepository extends JpaRepository<ShipmentEntity, String> {

    Optional<ShipmentEntity> findByOrderId(String orderId);
}
