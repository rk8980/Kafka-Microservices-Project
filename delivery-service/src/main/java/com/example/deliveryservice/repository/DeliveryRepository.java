package com.example.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.deliveryservice.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
