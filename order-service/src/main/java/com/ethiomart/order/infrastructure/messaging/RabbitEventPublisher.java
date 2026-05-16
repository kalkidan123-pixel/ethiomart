package com.ethiomart.order.infrastructure.messaging;

import com.ethiomart.events.EventRoutingKeys;
import com.ethiomart.events.OrderCreatedEvent;
import com.ethiomart.order.application.port.out.EventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                EventRoutingKeys.EXCHANGE,
                EventRoutingKeys.ORDER_CREATED,
                event);
    }
}
