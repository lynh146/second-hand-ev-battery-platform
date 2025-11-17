package com.example.service;

import com.example.model.Payment;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPaymentService {
    
  
    Long createPaymentRecord(Long transactionId, BigDecimal totalAmount, String paymentMethod);
    void processMomoSuccess(String orderId, Map<String, Object> momoResponse);
    void processMomoFailure(String orderId, Map<String, Object> momoResponse);
    
     
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Long id);
    List<Payment> getPaymentsByTransactionId(Long transactionId);  
    void deletePayment(Long id);
}