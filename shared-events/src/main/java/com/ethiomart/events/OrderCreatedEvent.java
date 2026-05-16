package com.ethiomart.events;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderCreatedEvent(
        String eventId,
        String orderId,
        String userId,
        String productId,
        int quantity,
        BigDecimal amount,
        Instant occurredAt
) {
}
