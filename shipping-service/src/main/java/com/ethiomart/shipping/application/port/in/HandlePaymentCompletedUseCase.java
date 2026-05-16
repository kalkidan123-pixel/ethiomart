package com.ethiomart.shipping.application.port.in;

public interface HandlePaymentCompletedUseCase {

    void onPaymentCompleted(String orderId);
}
