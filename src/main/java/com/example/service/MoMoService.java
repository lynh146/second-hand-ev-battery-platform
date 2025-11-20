package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class MoMoService {

     
    public String createPaymentRequest(long amount, String description, Long paymentId) {
        
        return "/payment/status?orderId=" + paymentId + "&amount=" + amount;
    }

     
    public boolean validateSignature(String rawData, String signature) {
         
        return true;
    }
}
