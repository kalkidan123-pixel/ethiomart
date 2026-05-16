package com.ethiomart.payment.application.service;

import com.ethiomart.events.PaymentCompletedEvent;
import com.ethiomart.events.PaymentFailedEvent;
import com.ethiomart.payment.application.port.in.ProcessPaymentCommand;
import com.ethiomart.payment.application.port.in.ProcessPaymentUseCase;
import com.ethiomart.payment.application.port.out.EventPublisherPort;
import com.ethiomart.payment.domain.model.Payment;
import com.ethiomart.payment.domain.repository.PaymentRepository;
import com.ethiomart.payment.domain.service.PaymentProcessor;

import java.time.Instant;
import java.util.UUID;

public class ProcessPaymentService implements ProcessPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentProcessor paymentProcessor;
    private final EventPublisherPort eventPublisher;

    public ProcessPaymentService(
            PaymentRepository paymentRepository,
            PaymentProcessor paymentProcessor,
            EventPublisherPort eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentProcessor = paymentProcessor;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void process(ProcessPaymentCommand command) {
        if (paymentRepository.findByOrderId(command.orderId()).isPresent()) {
            return;
        }

        if (paymentProcessor.canProcess(command.amount())) {
            Payment payment = new Payment(command.orderId(), command.amount(), Payment.Status.COMPLETED);
            paymentRepository.save(payment);
            eventPublisher.publishPaymentCompleted(new PaymentCompletedEvent(
                    UUID.randomUUID().toString(),
                    command.orderId(),
                    payment.getId(),
                    command.amount(),
                    Instant.now()));
        } else {
            Payment payment = new Payment(command.orderId(), command.amount(), Payment.Status.FAILED);
            paymentRepository.save(payment);
            eventPublisher.publishPaymentFailed(new PaymentFailedEvent(
                    UUID.randomUUID().toString(),
                    command.orderId(),
                    "Invalid payment amount",
                    Instant.now()));
        }
    }
}
