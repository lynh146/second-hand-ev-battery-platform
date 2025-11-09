package com.example.service;

import com.example.model.Payment;
import java.util.List;
import java.util.Optional;

public interface IPaymentService {
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Long id);
    Payment createPayment(Payment payment);
    void deletePayment(Long id);
}
