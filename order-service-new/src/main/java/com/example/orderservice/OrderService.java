package com.example.orderservice;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.event.OrderCreatedEvent;
import com.example.orderservice.kafka.OrderKafkaProducer;
import com.example.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderKafkaProducer orderKafkaProducer;

    public OrderService(OrderRepository orderRepository,
                        OrderKafkaProducer orderKafkaProducer) {

        this.orderRepository = orderRepository;
        this.orderKafkaProducer = orderKafkaProducer;
    }

    public Order createOrder(Order order) {

        // Set initial order status
        order.setStatus("CREATED");

        // Save order in MySQL
        Order savedOrder = orderRepository.save(order);

        // Create Kafka event
        OrderCreatedEvent event = new OrderCreatedEvent();

        event.setEventId("EVT-" + UUID.randomUUID());
        event.setEventType("ORDER_CREATED");
        event.setOrderId(savedOrder.getOrderId());
        event.setCustomerId(savedOrder.getCustomerId());
        event.setAmount(savedOrder.getAmount());
        event.setDeliveryAddress(savedOrder.getDeliveryAddress());

        // Send event to Kafka
        orderKafkaProducer.sendOrderCreatedEvent(event);

        return savedOrder;
    }
}