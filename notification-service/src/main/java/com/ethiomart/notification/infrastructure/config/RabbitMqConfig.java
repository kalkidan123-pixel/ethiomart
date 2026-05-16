package com.ethiomart.notification.infrastructure.config;

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

    public static final String USER_REGISTERED_QUEUE = "notification.user.registered.queue";
    public static final String ORDER_CREATED_QUEUE = "notification.order.created.queue";
    public static final String PAYMENT_COMPLETED_QUEUE = "notification.payment.completed.queue";
    public static final String PAYMENT_FAILED_QUEUE = "notification.payment.failed.queue";
    public static final String STOCK_RESERVED_QUEUE = "notification.stock.reserved.queue";
    public static final String STOCK_FAILED_QUEUE = "notification.stock.failed.queue";
    public static final String SHIPMENT_CREATED_QUEUE = "notification.shipment.created.queue";

    @Bean
    TopicExchange appExchange() {
        return new TopicExchange(EventRoutingKeys.EXCHANGE, true, false);
    }

    @Bean
    Queue userRegisteredQueue() {
        return durableQueue(USER_REGISTERED_QUEUE);
    }

    @Bean
    Queue orderCreatedQueue() {
        return durableQueue(ORDER_CREATED_QUEUE);
    }

    @Bean
    Queue paymentCompletedQueue() {
        return durableQueue(PAYMENT_COMPLETED_QUEUE);
    }

    @Bean
    Queue paymentFailedQueue() {
        return durableQueue(PAYMENT_FAILED_QUEUE);
    }

    @Bean
    Queue stockReservedQueue() {
        return durableQueue(STOCK_RESERVED_QUEUE);
    }

    @Bean
    Queue stockFailedQueue() {
        return durableQueue(STOCK_FAILED_QUEUE);
    }

    @Bean
    Queue shipmentCreatedQueue() {
        return durableQueue(SHIPMENT_CREATED_QUEUE);
    }

    private Queue durableQueue(String name) {
        return QueueBuilder.durable(name)
                .withArgument("x-dead-letter-exchange", EventRoutingKeys.DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", name + ".dlq")
                .build();
    }

    @Bean
    Binding userRegisteredBinding(Queue userRegisteredQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(userRegisteredQueue).to(appExchange).with(EventRoutingKeys.USER_REGISTERED);
    }

    @Bean
    Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(appExchange).with(EventRoutingKeys.ORDER_CREATED);
    }

    @Bean
    Binding paymentCompletedBinding(Queue paymentCompletedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(paymentCompletedQueue).to(appExchange).with(EventRoutingKeys.PAYMENT_COMPLETED);
    }

    @Bean
    Binding paymentFailedBinding(Queue paymentFailedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(paymentFailedQueue).to(appExchange).with(EventRoutingKeys.PAYMENT_FAILED);
    }

    @Bean
    Binding stockReservedBinding(Queue stockReservedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(stockReservedQueue).to(appExchange).with(EventRoutingKeys.STOCK_RESERVED);
    }

    @Bean
    Binding stockFailedBinding(Queue stockFailedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(stockFailedQueue).to(appExchange).with(EventRoutingKeys.STOCK_FAILED);
    }

    @Bean
    Binding shipmentCreatedBinding(Queue shipmentCreatedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shipmentCreatedQueue).to(appExchange).with(EventRoutingKeys.SHIPMENT_CREATED);
    }

    @Bean
    MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
