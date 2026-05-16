package com.ethiomart.shipping.infrastructure.config;

import com.ethiomart.shipping.application.port.out.EventPublisherPort;
import com.ethiomart.shipping.application.service.ShippingCoordinationService;
import com.ethiomart.shipping.domain.repository.OrderReadinessRepository;
import com.ethiomart.shipping.domain.repository.ShipmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    ShippingCoordinationService shippingCoordinationService(
            OrderReadinessRepository readinessRepository,
            ShipmentRepository shipmentRepository,
            EventPublisherPort eventPublisher) {
        return new ShippingCoordinationService(readinessRepository, shipmentRepository, eventPublisher);
    }
}
