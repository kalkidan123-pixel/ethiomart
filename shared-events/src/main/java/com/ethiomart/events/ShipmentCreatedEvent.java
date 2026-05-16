package com.ethiomart.events;

import java.time.Instant;

public record ShipmentCreatedEvent(
        String eventId,
        String orderId,
        String shipmentId,
        String trackingNumber,
        Instant occurredAt
) {
}
