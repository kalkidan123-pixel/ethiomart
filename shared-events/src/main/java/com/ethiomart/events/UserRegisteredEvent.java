package com.ethiomart.events;

import java.time.Instant;

public record UserRegisteredEvent(
        String eventId,
        String userId,
        String email,
        String fullName,
        Instant occurredAt
) {
}
