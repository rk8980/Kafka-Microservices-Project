package com.example.notificationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationKafkaConsumer {

    // Existing successful delivery notification
    @KafkaListener(
            topics = "delivery-created",
            groupId = "notification-service-group"
    )
    public void consumeDeliveryCreated(DeliveryCreatedEvent event) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("Notification sent to customer");
        System.out.println();
        System.out.println("Customer ID: " + event.getCustomerId());
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Delivery ID: " + event.getDeliveryId());
        System.out.println("Delivery Address: " + event.getDeliveryAddress());
        System.out.println("Delivery Status: " + event.getDeliveryStatus());
        System.out.println();
        System.out.println("Message: Your order has been successfully created for delivery.");
        System.out.println("======================================");
    }


    // New payment failure notification
    @KafkaListener(
            topics = "payment-failed",
            groupId = "notification-payment-failed-group"
    )
    public void consumePaymentFailed(PaymentFailedEvent event) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("PAYMENT FAILURE NOTIFICATION");
        System.out.println();
        System.out.println("Customer ID: " + event.getCustomerId());
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Amount: " + event.getAmount());
        System.out.println("Payment ID: " + event.getPaymentId());
        System.out.println("Payment Method: " + event.getPaymentMethod());
        System.out.println("Payment Status: " + event.getPaymentStatus());
        System.out.println("Reason: " + event.getReason());
        System.out.println();
        System.out.println("Message: Your payment for Order ID "
                + event.getOrderId()
                + " has failed. Please retry the payment.");
        System.out.println("======================================");
    }
}