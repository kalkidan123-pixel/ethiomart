package com.ethiomart.notification.domain.model;

import java.time.Instant;

public class Notification {

    private final String eventType;
    private final String message;
    private final Instant createdAt;

    public Notification(String eventType, String message) {
        this.eventType = eventType;
        this.message = message;
        this.createdAt = Instant.now();
    }

    public String getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
