package com.example.paymentservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.kafka.PaymentFailedEvent;
import com.example.paymentservice.kafka.PaymentKafkaProducer;
import com.example.paymentservice.kafka.PaymentSuccessEvent;
import com.example.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentKafkaProducer paymentKafkaProducer;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentKafkaProducer paymentKafkaProducer) {

        this.paymentRepository = paymentRepository;
        this.paymentKafkaProducer = paymentKafkaProducer;
    }

    public Payment processPayment(Long orderId,Long customerId,Double amount,String paymentMethod, String deliveryAddress) 
    	{

        Payment payment = new Payment();

        payment.setOrderId(orderId);
        payment.setCustomerId(customerId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);

        if (amount != null && amount > 0) {

            payment.setPaymentStatus("SUCCESS");
            payment.setTransactionId("TXN-" + UUID.randomUUID());
            payment.setReason(null);

        } else {

            payment.setPaymentStatus("FAILED");
            payment.setTransactionId("TXN-" + UUID.randomUUID());
            payment.setReason("INVALID_AMOUNT");
        }

        Payment savedPayment = paymentRepository.save(payment);
        if ("SUCCESS".equals(savedPayment.getPaymentStatus())) {

            PaymentSuccessEvent event = new PaymentSuccessEvent(
                "PAY-" + UUID.randomUUID(),
                "PAYMENT_SUCCESS",
                savedPayment.getOrderId(),
                savedPayment.getCustomerId(),
                savedPayment.getAmount(),
                savedPayment.getPaymentId(),
                savedPayment.getPaymentMethod(),
                savedPayment.getPaymentStatus(),deliveryAddress
                
            );

            paymentKafkaProducer.sendPaymentSuccess(event);

        } else {

            PaymentFailedEvent event = new PaymentFailedEvent(
                "PAY-" + UUID.randomUUID(),
                "PAYMENT_FAILED",
                savedPayment.getOrderId(),
                savedPayment.getCustomerId(),
                savedPayment.getAmount(),
                savedPayment.getPaymentId(),
                savedPayment.getPaymentMethod(),
                savedPayment.getPaymentStatus(),
                savedPayment.getReason()
            );

            paymentKafkaProducer.sendPaymentFailed(event);
        }

        return savedPayment;
    }
}