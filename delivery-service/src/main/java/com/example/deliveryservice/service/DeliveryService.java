package com.example.deliveryservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.deliveryservice.entity.Delivery;
import com.example.deliveryservice.kafka.DeliveryCreatedEvent;
import com.example.deliveryservice.kafka.DeliveryKafkaProducer;
import com.example.deliveryservice.repository.DeliveryRepository;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryKafkaProducer deliveryKafkaProducer;

    public DeliveryService(DeliveryRepository deliveryRepository,
                           DeliveryKafkaProducer deliveryKafkaProducer) {

        this.deliveryRepository = deliveryRepository;
        this.deliveryKafkaProducer = deliveryKafkaProducer;
    }

    public Delivery createDelivery(Long orderId,
                                   Long customerId,
                                   String deliveryAddress) {

        Delivery delivery = new Delivery();

        delivery.setOrderId(orderId);
        delivery.setCustomerId(customerId);
        delivery.setDeliveryAddress(deliveryAddress);
        delivery.setDeliveryStatus("CREATED");

        Delivery savedDelivery = deliveryRepository.save(delivery);

        DeliveryCreatedEvent event = new DeliveryCreatedEvent(
            "DEL-" + UUID.randomUUID(),
            "DELIVERY_CREATED",
            savedDelivery.getOrderId(),
            savedDelivery.getCustomerId(),
            savedDelivery.getDeliveryId(),
            savedDelivery.getDeliveryAddress(),
            savedDelivery.getDeliveryStatus()
        );

        deliveryKafkaProducer.sendDeliveryCreated(event);

        return savedDelivery;
    }
}