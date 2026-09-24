package com.example.paymentservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentSuccess(PaymentSuccessEvent event) {

        kafkaTemplate.send("payment-success", event.getOrderId().toString(), event);

        System.out.println("PAYMENT_SUCCESS event published");
        System.out.println("Order ID: " + event.getOrderId());
    }
    
    
    public void sendPaymentFailed(PaymentFailedEvent event) {

        kafkaTemplate.send(
            "payment-failed",
            event.getOrderId().toString(),
            event
        );

        System.out.println("PAYMENT_FAILED event published");
        System.out.println("Order ID: " + event.getOrderId());
    }
}