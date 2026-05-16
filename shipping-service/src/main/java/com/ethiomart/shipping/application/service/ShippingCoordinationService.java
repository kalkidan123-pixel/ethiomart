package com.ethiomart.shipping.application.service;

import com.ethiomart.events.ShipmentCreatedEvent;
import com.ethiomart.shipping.application.port.in.HandlePaymentCompletedUseCase;
import com.ethiomart.shipping.application.port.in.HandleStockReservedUseCase;
import com.ethiomart.shipping.application.port.out.EventPublisherPort;
import com.ethiomart.shipping.domain.model.OrderReadiness;
import com.ethiomart.shipping.domain.model.Shipment;
import com.ethiomart.shipping.domain.repository.OrderReadinessRepository;
import com.ethiomart.shipping.domain.repository.ShipmentRepository;

import java.time.Instant;
import java.util.UUID;

public class ShippingCoordinationService implements HandlePaymentCompletedUseCase, HandleStockReservedUseCase {

    private final OrderReadinessRepository readinessRepository;
    private final ShipmentRepository shipmentRepository;
    private final EventPublisherPort eventPublisher;

    public ShippingCoordinationService(
            OrderReadinessRepository readinessRepository,
            ShipmentRepository shipmentRepository,
            EventPublisherPort eventPublisher) {
        this.readinessRepository = readinessRepository;
        this.shipmentRepository = shipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onPaymentCompleted(String orderId) {
        updateReadiness(orderId, true, false);
    }

    @Override
    public void onStockReserved(String orderId) {
        updateReadiness(orderId, false, true);
    }

    private void updateReadiness(String orderId, boolean payment, boolean stock) {
        if (shipmentRepository.findByOrderId(orderId).isPresent()) {
            return;
        }

        OrderReadiness readiness = readinessRepository.getOrCreate(orderId);
        if (payment) {
            readiness.markPaymentCompleted();
        }
        if (stock) {
            readiness.markStockReserved();
        }
        readinessRepository.save(readiness);

        if (readiness.isReadyForShipment()) {
            createShipment(readiness);
        }
    }

    private void createShipment(OrderReadiness readiness) {
        Shipment shipment = new Shipment(readiness.getOrderId());
        shipmentRepository.save(shipment);
        readiness.markShipmentCreated();
        readinessRepository.save(readiness);

        eventPublisher.publishShipmentCreated(new ShipmentCreatedEvent(
                UUID.randomUUID().toString(),
                shipment.getOrderId(),
                shipment.getId(),
                shipment.getTrackingNumber(),
                Instant.now()));
    }
}
