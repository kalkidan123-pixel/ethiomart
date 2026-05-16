package com.ethiomart.notification.infrastructure.config;

import com.ethiomart.notification.application.port.in.SendNotificationUseCase;
import com.ethiomart.notification.application.port.out.NotificationDeliveryPort;
import com.ethiomart.notification.application.service.SendNotificationService;
import com.ethiomart.notification.domain.service.NotificationFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    NotificationFormatter notificationFormatter() {
        return new NotificationFormatter();
    }

    @Bean
    SendNotificationUseCase sendNotificationUseCase(
            NotificationFormatter formatter,
            NotificationDeliveryPort deliveryPort) {
        return new SendNotificationService(formatter, deliveryPort);
    }
}
