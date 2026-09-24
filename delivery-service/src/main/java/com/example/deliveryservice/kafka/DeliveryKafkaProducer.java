package com.example.deliveryservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeliveryKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DeliveryKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDeliveryCreated(DeliveryCreatedEvent event) {

        kafkaTemplate.send(
            "delivery-created",
            event.getOrderId().toString(),
            event
        );

        System.out.println("DELIVERY_CREATED event published");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Delivery address: " + event.getDeliveryAddress());
    }
}