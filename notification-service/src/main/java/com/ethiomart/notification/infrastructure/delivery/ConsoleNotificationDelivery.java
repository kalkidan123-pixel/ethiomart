package com.ethiomart.notification.infrastructure.delivery;

import com.ethiomart.notification.application.port.out.NotificationDeliveryPort;
import com.ethiomart.notification.domain.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotificationDelivery implements NotificationDeliveryPort {

    private static final Logger log = LoggerFactory.getLogger(ConsoleNotificationDelivery.class);

    @Override
    public void deliver(Notification notification) {
        log.info("NOTIFICATION => {}", notification.getMessage());
        System.out.println("NOTIFICATION => " + notification.getMessage());
    }
}
