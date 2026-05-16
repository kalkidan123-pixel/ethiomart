package com.ethiomart.shipping.presentation.controller;

import com.ethiomart.shipping.domain.repository.ShipmentRepository;
import com.ethiomart.shipping.presentation.dto.ShipmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipments")
@Tag(name = "Shipments")
public class ShipmentQueryController {

    private final ShipmentRepository shipmentRepository;

    public ShipmentQueryController(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get shipment by order id")
    public ResponseEntity<ShipmentResponse> get(@PathVariable String orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .map(s -> new ShipmentResponse(s.getId(), s.getOrderId(), s.getTrackingNumber()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
