package com.ethiomart.order.infrastructure.config;

import com.ethiomart.order.application.port.in.CreateOrderUseCase;
import com.ethiomart.order.application.port.out.EventPublisherPort;
import com.ethiomart.order.application.service.CreateOrderService;
import com.ethiomart.order.domain.repository.OrderRepository;
import com.ethiomart.order.domain.service.PricingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    PricingService pricingService() {
        return new PricingService();
    }

    @Bean
    CreateOrderUseCase createOrderUseCase(
            OrderRepository orderRepository,
            PricingService pricingService,
            EventPublisherPort eventPublisher) {
        return new CreateOrderService(orderRepository, pricingService, eventPublisher);
    }
}
