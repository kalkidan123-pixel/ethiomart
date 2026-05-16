package com.ethiomart.events;

import java.time.Instant;

public record PaymentFailedEvent(
        String eventId,
        String orderId,
        String reason,
        Instant occurredAt
) {
}
