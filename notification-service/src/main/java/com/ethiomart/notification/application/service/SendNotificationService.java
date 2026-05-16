package com.ethiomart.notification.application.service;

import com.ethiomart.notification.application.port.in.SendNotificationUseCase;
import com.ethiomart.notification.application.port.out.NotificationDeliveryPort;
import com.ethiomart.notification.domain.model.Notification;
import com.ethiomart.notification.domain.service.NotificationFormatter;

public class SendNotificationService implements SendNotificationUseCase {

    private final NotificationFormatter formatter;
    private final NotificationDeliveryPort deliveryPort;

    public SendNotificationService(NotificationFormatter formatter, NotificationDeliveryPort deliveryPort) {
        this.formatter = formatter;
        this.deliveryPort = deliveryPort;
    }

    @Override
    public void send(String eventType, String message) {
        Notification notification = new Notification(eventType, formatter.format(eventType, message));
        deliveryPort.deliver(notification);
    }
}
