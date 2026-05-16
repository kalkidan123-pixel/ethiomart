package com.ethiomart.shipping.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ethiomart.events.EventRoutingKeys;

@Configuration
public class RabbitMqConfig {

    public static final String PAYMENT_COMPLETED_QUEUE = "shipping.payment.completed.queue";
    public static final String STOCK_RESERVED_QUEUE = "shipping.stock.reserved.queue";

    @Bean
    TopicExchange appExchange() {
        return new TopicExchange(EventRoutingKeys.EXCHANGE, true, false);
    }

    @Bean
    TopicExchange deadLetterExchange() {
        return new TopicExchange(EventRoutingKeys.DLX_EXCHANGE, true, false);
    }

    @Bean
    Queue paymentCompletedQueue() {
        return QueueBuilder.durable(PAYMENT_COMPLETED_QUEUE)
                .withArgument("x-dead-letter-exchange", EventRoutingKeys.DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", PAYMENT_COMPLETED_QUEUE + ".dlq")
                .build();
    }

    @Bean
    Queue stockReservedQueue() {
        return QueueBuilder.durable(STOCK_RESERVED_QUEUE)
                .withArgument("x-dead-letter-exchange", EventRoutingKeys.DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", STOCK_RESERVED_QUEUE + ".dlq")
                .build();
    }

    @Bean
    Binding paymentCompletedBinding(@Qualifier("paymentCompletedQueue") Queue paymentCompletedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(paymentCompletedQueue).to(appExchange).with(EventRoutingKeys.PAYMENT_COMPLETED);
    }

    @Bean
    Binding stockReservedBinding(@Qualifier("stockReservedQueue") Queue stockReservedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(stockReservedQueue).to(appExchange).with(EventRoutingKeys.STOCK_RESERVED);
    }

    @Bean
    MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
