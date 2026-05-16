package com.ethiomart.notification.application.port.in;

public interface SendNotificationUseCase {

    void send(String eventType, String message);
}
