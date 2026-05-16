package com.ethiomart.payment.infrastructure.messaging;

import com.ethiomart.events.EventRoutingKeys;
import com.ethiomart.events.PaymentCompletedEvent;
import com.ethiomart.events.PaymentFailedEvent;
import com.ethiomart.payment.application.port.out.EventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        rabbitTemplate.convertAndSend(
                EventRoutingKeys.EXCHANGE,
                EventRoutingKeys.PAYMENT_COMPLETED,
                event);
    }

    @Override
    public void publishPaymentFailed(PaymentFailedEvent event) {
        rabbitTemplate.convertAndSend(
                EventRoutingKeys.EXCHANGE,
                EventRoutingKeys.PAYMENT_FAILED,
                event);
    }
}
