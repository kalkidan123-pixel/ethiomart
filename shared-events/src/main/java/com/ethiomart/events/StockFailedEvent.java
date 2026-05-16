package com.ethiomart.events;

import java.time.Instant;

public record StockFailedEvent(
        String eventId,
        String orderId,
        String productId,
        String reason,
        Instant occurredAt
) {
}
