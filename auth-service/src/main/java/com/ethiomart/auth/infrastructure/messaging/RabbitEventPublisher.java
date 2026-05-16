package com.ethiomart.auth.infrastructure.messaging;

import com.ethiomart.auth.application.port.out.EventPublisherPort;
import com.ethiomart.events.EventRoutingKeys;
import com.ethiomart.events.UserRegisteredEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserRegistered(UserRegisteredEvent event) {
        rabbitTemplate.convertAndSend(
                EventRoutingKeys.EXCHANGE,
                EventRoutingKeys.USER_REGISTERED,
                event);
    }
}
