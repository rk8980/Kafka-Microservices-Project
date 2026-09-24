package com.example.deliveryservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.deliveryservice.entity.Delivery;
import com.example.deliveryservice.service.DeliveryService;

@Component
public class DeliveryKafkaConsumer {

    private final DeliveryService deliveryService;

    public DeliveryKafkaConsumer(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @KafkaListener(
        topics = "payment-success",
        groupId = "delivery-service-group"
    )
    public void consumePaymentSuccess(PaymentSuccessEvent event) {

        System.out.println("PAYMENT_SUCCESS event received");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Customer ID: " + event.getCustomerId());
        System.out.println("Delivery Address: " + event.getDeliveryAddress());

        Delivery delivery = deliveryService.createDelivery(
            event.getOrderId(),
            event.getCustomerId(),
            event.getDeliveryAddress()
        );

        System.out.println("Delivery created");
        System.out.println("Delivery ID: " + delivery.getDeliveryId());
        System.out.println("Delivery Status: " + delivery.getDeliveryStatus());
    }
}