package com.ethiomart.shipping.infrastructure.messaging;

import com.ethiomart.events.EventRoutingKeys;
import com.ethiomart.events.ShipmentCreatedEvent;
import com.ethiomart.shipping.application.port.out.EventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishShipmentCreated(ShipmentCreatedEvent event) {
        rabbitTemplate.convertAndSend(EventRoutingKeys.EXCHANGE, EventRoutingKeys.SHIPMENT_CREATED, event);
    }
}
