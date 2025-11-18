package com.example.service.impl;

import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import com.example.service.IPaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
