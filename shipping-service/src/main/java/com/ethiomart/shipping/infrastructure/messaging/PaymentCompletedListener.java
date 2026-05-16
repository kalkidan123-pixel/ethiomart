package com.ethiomart.shipping.infrastructure.messaging;

import com.ethiomart.events.PaymentCompletedEvent;
import com.ethiomart.shipping.application.service.ShippingCoordinationService;
import com.ethiomart.shipping.infrastructure.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentCompletedListener.class);
    private final ShippingCoordinationService coordinationService;

    public PaymentCompletedListener(ShippingCoordinationService coordinationService) {
        this.coordinationService = coordinationService;
    }

    @RabbitListener(queues = RabbitMqConfig.PAYMENT_COMPLETED_QUEUE)
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received payment.completed for order {}", event.orderId());
        coordinationService.onPaymentCompleted(event.orderId());
    }
}
