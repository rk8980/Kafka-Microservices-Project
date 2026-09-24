package com.example.paymentservice.kafka;

import java.util.HashSet;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.service.PaymentService;

@Component
public class PaymentKafkaConsumer {

    private final PaymentService paymentService;

    // Stores event IDs that have already been processed
    private final Set<String> processedEventIds = new HashSet<>();

    public PaymentKafkaConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "payment-service-group"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        System.out.println("=======================================");
        System.out.println("ORDER_CREATED event received");
        System.out.println("Event ID: " + event.getEventId());
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Customer ID: " + event.getCustomerId());
        System.out.println("Amount: " + event.getAmount());
        System.out.println("========================================");

        // Check for duplicate event
        if (processedEventIds.contains(event.getEventId())) {

            System.out.println("DUPLICATE EVENT DETECTED!");
            System.out.println("Event ID: " + event.getEventId());
            System.out.println("Order ID: " + event.getOrderId());
            System.out.println("Payment will NOT be processed again.");
            System.out.println("========================================");

            return;
        }

        // Failure
        if (event.getAmount() == 99999) {

            System.out.println("Test Failure : Payment processing failed!");

            throw new RuntimeException("payment processing failure");
        }

        Payment payment = paymentService.processPayment(
                event.getOrderId(),
                event.getCustomerId(),
                event.getAmount(),
                "UPI",
                event.getDeliveryAddress()
        );

        // Remember the event after successful processing
        processedEventIds.add(event.getEventId());

        System.out.println("Payment processed");
        System.out.println("Payment Status: " + payment.getPaymentStatus());
        System.out.println("Transaction ID: " + payment.getTransactionId());
    }
}