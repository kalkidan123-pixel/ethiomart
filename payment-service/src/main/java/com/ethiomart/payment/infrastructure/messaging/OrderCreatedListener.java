package com.ethiomart.payment.infrastructure.messaging;

import com.ethiomart.events.OrderCreatedEvent;
import com.ethiomart.payment.application.port.in.ProcessPaymentCommand;
import com.ethiomart.payment.application.port.in.ProcessPaymentUseCase;
import com.ethiomart.payment.infrastructure.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final ProcessPaymentUseCase processPaymentUseCase;

    public OrderCreatedListener(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Received order.created for order {}", event.orderId());
        processPaymentUseCase.process(new ProcessPaymentCommand(event.orderId(), event.amount()));
    }
}
