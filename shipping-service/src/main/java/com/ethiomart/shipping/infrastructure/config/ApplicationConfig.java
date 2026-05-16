package com.ethiomart.shipping.infrastructure.config;

import com.ethiomart.shipping.application.port.in.HandlePaymentCompletedUseCase;
import com.ethiomart.shipping.application.port.in.HandleStockReservedUseCase;
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

    @Bean
    HandlePaymentCompletedUseCase handlePaymentCompletedUseCase(ShippingCoordinationService service) {
        return service;
    }

    @Bean
    HandleStockReservedUseCase handleStockReservedUseCase(ShippingCoordinationService service) {
        return service;
    }
}
