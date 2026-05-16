package com.ethiomart.shipping.infrastructure.persistence.adapter;

import com.ethiomart.shipping.domain.model.Shipment;
import com.ethiomart.shipping.domain.repository.ShipmentRepository;
import com.ethiomart.shipping.infrastructure.persistence.entity.ShipmentEntity;
import com.ethiomart.shipping.infrastructure.persistence.repository.ShipmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShipmentRepositoryAdapter implements ShipmentRepository {

    private final ShipmentJpaRepository jpaRepository;

    public ShipmentRepositoryAdapter(ShipmentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        jpaRepository.save(new ShipmentEntity(
                shipment.getId(), shipment.getOrderId(), shipment.getTrackingNumber()));
        return shipment;
    }

    @Override
    public Optional<Shipment> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId)
                .map(e -> new Shipment(e.getId(), orderId, e.getTrackingNumber()));
    }
}
