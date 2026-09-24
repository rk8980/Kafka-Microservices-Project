package com.example.notificationservice.kafka;

public class DeliveryCreatedEvent {

    private String eventId;
    private String eventType;
    private Long deliveryId;
    private Long orderId;
    private Long customerId;
    private String deliveryAddress;
    private String deliveryStatus;

    public DeliveryCreatedEvent() {
    }

    public DeliveryCreatedEvent(
            String eventId,
            String eventType,
            Long deliveryId,
            Long orderId,
            Long customerId,
            String deliveryAddress,
            String deliveryStatus) {

        this.eventId = eventId;
        this.eventType = eventType;
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryStatus = deliveryStatus;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}