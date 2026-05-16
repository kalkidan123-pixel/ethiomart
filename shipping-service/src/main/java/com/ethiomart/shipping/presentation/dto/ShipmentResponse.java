package com.ethiomart.shipping.presentation.dto;

public record ShipmentResponse(String shipmentId, String orderId, String trackingNumber) {
}
