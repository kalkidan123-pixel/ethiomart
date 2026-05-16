package com.ethiomart.payment.application.port.out;

import com.ethiomart.events.PaymentCompletedEvent;
import com.ethiomart.events.PaymentFailedEvent;

public interface EventPublisherPort {

    void publishPaymentCompleted(PaymentCompletedEvent event);

    void publishPaymentFailed(PaymentFailedEvent event);
}
