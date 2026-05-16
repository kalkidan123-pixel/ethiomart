package com.ethiomart.events;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentCompletedEvent(
        String eventId,
        String orderId,
        String paymentId,
        BigDecimal amount,
        Instant occurredAt
) {
}
