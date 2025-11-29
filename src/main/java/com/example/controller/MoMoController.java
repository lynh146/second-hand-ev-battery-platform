package com.example.controller;

import com.example.service.IPaymentService;
import com.example.service.MomoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/momo")
public class MoMoController {

    private final MomoService momoService;
    private final IPaymentService paymentService;

    @PostMapping("/ipn")
    public ResponseEntity<String> handleMomoIpn(@RequestBody Map<String, Object> body) {
        boolean ok = momoService.verifySignature(body);
        if (!ok) return ResponseEntity.status(400).body("invalid signature");

        String orderId = String.valueOf(body.get("orderId"));
        String resultCode = String.valueOf(body.get("resultCode"));

        if ("0".equals(resultCode)) {
            paymentService.processMomoSuccess(orderId, body);
        } else {
            paymentService.processMomoFailure(orderId, body);
        }

        return ResponseEntity.noContent().build();
    }
}
