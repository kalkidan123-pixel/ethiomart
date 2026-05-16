package com.ethiomart.events;

public final class EventRoutingKeys {

    public static final String EXCHANGE = "app.exchange";
    public static final String DLX_EXCHANGE = "app.exchange.dlx";

    public static final String USER_REGISTERED = "user.registered";
    public static final String ORDER_CREATED = "order.created";
    public static final String PAYMENT_COMPLETED = "payment.completed";
    public static final String PAYMENT_FAILED = "payment.failed";
    public static final String STOCK_RESERVED = "stock.reserved";
    public static final String STOCK_FAILED = "stock.failed";
    public static final String SHIPMENT_CREATED = "shipment.created";

    private EventRoutingKeys() {
    }
}
