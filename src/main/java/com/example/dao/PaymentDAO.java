package com.example.dao;

import com.example.model.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentDAO {
    List<Payment> findAll();
    Optional<Payment> findById(Long id);
    Payment save(Payment payment);
    void deleteById(Long id);
}
