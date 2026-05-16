package com.ethiomart.payment.infrastructure.config;

import com.ethiomart.payment.application.port.in.ProcessPaymentUseCase;
import com.ethiomart.payment.application.port.out.EventPublisherPort;
import com.ethiomart.payment.application.service.ProcessPaymentService;
import com.ethiomart.payment.domain.repository.PaymentRepository;
import com.ethiomart.payment.domain.service.PaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    PaymentProcessor paymentProcessor() {
        return new PaymentProcessor();
    }

    @Bean
    ProcessPaymentUseCase processPaymentUseCase(
            PaymentRepository paymentRepository,
            PaymentProcessor paymentProcessor,
            EventPublisherPort eventPublisher) {
        return new ProcessPaymentService(paymentRepository, paymentProcessor, eventPublisher);
    }
}
