package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class MoMoService {

    // Create a payment request URL for MoMo (stub implementation)
    public String createPaymentRequest(long amount, String description, Long paymentId) {
        // In real implementation, build request to MoMo API and return redirect URL
        return "/payment/status?orderId=" + paymentId + "&amount=" + amount;
    }

    // Validate signature for IPN callbacks (stub - accept all for now)
    public boolean validateSignature(String rawData, String signature) {
        // TODO: implement real HMAC/SHA256 signature verification using MoMo secret
        return true;
    }
}
