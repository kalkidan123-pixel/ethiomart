package com.ethiomart.notification.application.port.out;

import com.ethiomart.notification.domain.model.Notification;

public interface NotificationDeliveryPort {

    void deliver(Notification notification);
}
