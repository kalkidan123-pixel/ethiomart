package com.ethiomart.inventory.infrastructure.config;

import com.ethiomart.events.EventRoutingKeys;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String ORDER_CREATED_QUEUE = "inventory.order.created.queue";
    public static final String ORDER_CREATED_DLQ = "inventory.order.created.dlq";

    @Bean
    TopicExchange appExchange() {
        return new TopicExchange(EventRoutingKeys.EXCHANGE, true, false);
    }

    @Bean
    TopicExchange deadLetterExchange() {
        return new TopicExchange(EventRoutingKeys.DLX_EXCHANGE, true, false);
    }

    @Bean
    Queue orderCreatedQueue() {
        return QueueBuilder.durable(ORDER_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", EventRoutingKeys.DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ORDER_CREATED_DLQ)
                .build();
    }

    @Bean
    Queue orderCreatedDlq() {
        return QueueBuilder.durable(ORDER_CREATED_DLQ).build();
    }

    @Bean
    Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(appExchange).with(EventRoutingKeys.ORDER_CREATED);
    }

    @Bean
    Binding orderCreatedDlqBinding(Queue orderCreatedDlq, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(orderCreatedDlq).to(deadLetterExchange).with(ORDER_CREATED_DLQ);
    }

    @Bean
    MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
