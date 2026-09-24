package com.example.orderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.OrderService;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService)
	{
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@RequestBody Order order)
	{
		
		Order saveOrder = orderService.createOrder(order);
		
		OrderResponse response = new OrderResponse(saveOrder.getOrderId(), saveOrder.getStatus());
		return ResponseEntity.ok(response);
	}

}
