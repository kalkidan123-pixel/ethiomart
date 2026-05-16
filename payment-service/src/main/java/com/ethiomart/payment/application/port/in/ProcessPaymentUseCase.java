package com.ethiomart.payment.application.port.in;

public interface ProcessPaymentUseCase {

    void process(ProcessPaymentCommand command);
}
