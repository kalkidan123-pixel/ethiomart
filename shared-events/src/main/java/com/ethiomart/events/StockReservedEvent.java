package com.ethiomart.events;

import java.time.Instant;

public record StockReservedEvent(
        String eventId,
        String orderId,
        String productId,
        int quantity,
        Instant occurredAt
) {
}
