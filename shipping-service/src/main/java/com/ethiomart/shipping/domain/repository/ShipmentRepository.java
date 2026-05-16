package com.ethiomart.shipping.domain.repository;

import com.ethiomart.shipping.domain.model.Shipment;

import java.util.Optional;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);

    Optional<Shipment> findByOrderId(String orderId);
}
