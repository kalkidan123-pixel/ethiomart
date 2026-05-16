package com.ethiomart.shipping.application.port.in;

public interface HandleStockReservedUseCase {

    void onStockReserved(String orderId);
}
