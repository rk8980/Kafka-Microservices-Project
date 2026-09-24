package com.example.notificationservice.service;

import com.example.notificationservice.kafka.DeliveryCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(DeliveryCreatedEvent event) {

        System.out.println("======================================");
        System.out.println("NOTIFICATION SERVICE");
        System.out.println("======================================");

        System.out.println("Notification sent to customer");
        System.out.println("Customer ID: " + event.getCustomerId());
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Delivery ID: " + event.getDeliveryId());
        System.out.println("Delivery Address: " + event.getDeliveryAddress());
        System.out.println("Delivery Status: " + event.getDeliveryStatus());

        System.out.println(
                "Message: Your order has been successfully created for delivery."
        );

        System.out.println("======================================");
    }
}