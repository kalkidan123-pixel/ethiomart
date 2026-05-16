package com.ethiomart.notification.infrastructure.messaging;

import com.ethiomart.events.OrderCreatedEvent;
import com.ethiomart.events.PaymentCompletedEvent;
import com.ethiomart.events.PaymentFailedEvent;
import com.ethiomart.events.ShipmentCreatedEvent;
import com.ethiomart.events.StockFailedEvent;
import com.ethiomart.events.StockReservedEvent;
import com.ethiomart.events.UserRegisteredEvent;
import com.ethiomart.notification.application.port.in.SendNotificationUseCase;
import com.ethiomart.notification.infrastructure.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AllEventsListener {

    private final SendNotificationUseCase sendNotificationUseCase;

    public AllEventsListener(SendNotificationUseCase sendNotificationUseCase) {
        this.sendNotificationUseCase = sendNotificationUseCase;
    }

    @RabbitListener(queues = RabbitMqConfig.USER_REGISTERED_QUEUE)
    public void onUserRegistered(UserRegisteredEvent event) {
        sendNotificationUseCase.send("user.registered",
                "Welcome " + event.fullName() + " (" + event.email() + ")");
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void onOrderCreated(OrderCreatedEvent event) {
        sendNotificationUseCase.send("order.created",
                "Order " + event.orderId() + " created for user " + event.userId()
                        + " amount " + event.amount());
    }

    @RabbitListener(queues = RabbitMqConfig.PAYMENT_COMPLETED_QUEUE)
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        sendNotificationUseCase.send("payment.completed",
                "Payment " + event.paymentId() + " completed for order " + event.orderId());
    }

    @RabbitListener(queues = RabbitMqConfig.PAYMENT_FAILED_QUEUE)
    public void onPaymentFailed(PaymentFailedEvent event) {
        sendNotificationUseCase.send("payment.failed",
                "Payment failed for order " + event.orderId() + ": " + event.reason());
    }

    @RabbitListener(queues = RabbitMqConfig.STOCK_RESERVED_QUEUE)
    public void onStockReserved(StockReservedEvent event) {
        sendNotificationUseCase.send("stock.reserved",
                "Stock reserved for order " + event.orderId() + " product " + event.productId());
    }

    @RabbitListener(queues = RabbitMqConfig.STOCK_FAILED_QUEUE)
    public void onStockFailed(StockFailedEvent event) {
        sendNotificationUseCase.send("stock.failed",
                "Stock failed for order " + event.orderId() + ": " + event.reason());
    }

    @RabbitListener(queues = RabbitMqConfig.SHIPMENT_CREATED_QUEUE)
    public void onShipmentCreated(ShipmentCreatedEvent event) {
        sendNotificationUseCase.send("shipment.created",
                "Shipment " + event.shipmentId() + " created. Tracking: " + event.trackingNumber());
    }
}
