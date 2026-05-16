package com.ethiomart.notification.domain.service;

public class NotificationFormatter {

    public String format(String eventType, String details) {
        return "[" + eventType + "] " + details;
    }
}
