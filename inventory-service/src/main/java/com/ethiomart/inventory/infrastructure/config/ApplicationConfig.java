package com.ethiomart.inventory.infrastructure.config;

import com.ethiomart.inventory.application.port.in.ReserveStockUseCase;
import com.ethiomart.inventory.application.port.out.EventPublisherPort;
import com.ethiomart.inventory.application.service.ReserveStockService;
import com.ethiomart.inventory.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    ReserveStockUseCase reserveStockUseCase(
            ProductRepository productRepository,
            EventPublisherPort eventPublisher) {
        return new ReserveStockService(productRepository, eventPublisher);
    }
}
