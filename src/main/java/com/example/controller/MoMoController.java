package com.example.controller;

import com.example.service.IPaymentService;
import com.example.service.MoMoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/momo")
@RequiredArgsConstructor
public class MoMoController {

    private final MoMoService momoService;
    private final IPaymentService paymentService;

    @PostMapping("/ipn")
    public ResponseEntity<String> handleMomoIpn(@RequestBody Map<String, Object> momoResponse) {
        
        String momoSignature = (String) momoResponse.get("signature");
        String orderId = (String) momoResponse.get("orderId"); 
        
        // Cần đảm bảo hàm này tái tạo Raw Data chính xác như MoMo yêu cầu
        String rawData = createMomoIpnRawData(momoResponse); 
        
        if (momoService.validateSignature(rawData, momoSignature)) {
            
            String resultCode = String.valueOf(momoResponse.get("resultCode"));
            
            // CẬP NHẬT DB (Chạy qua Service)
            if ("0".equals(resultCode)) {
                paymentService.processMomoSuccess(orderId, momoResponse);
            } else {
                paymentService.processMomoFailure(orderId, momoResponse);
            }
            
            return ResponseEntity.noContent().build();
            
        } else {
            return ResponseEntity.status(400).body("INVALID SIGNATURE");
        }
    }

    @GetMapping("/return")
    public String handleMomoReturn(@RequestParam Map<String, String> params) {
        String orderId = params.get("orderId"); 
        String resultCode = params.get("resultCode");
        
        if ("0".equals(resultCode)) {
            return "redirect:/payment/status?orderId=" + orderId + "&result=success";
        } else {
            return "redirect:/payment/status?orderId=" + orderId + "&result=failure";
        }
    }
    
    // HÀM TIỆN ÍCH TẠO RAW DATA CHO IPN (BẮT BUỘC)
    private String createMomoIpnRawData(Map<String, Object> response) {
        // Dựa trên tài liệu MoMo v2.0/IPN: Thứ tự BẮT BUỘC
        return "accessKey=" + response.get("accessKey") +
               "&amount=" + response.get("amount") +
               "&extraData=" + response.get("extraData") + 
               "&message=" + response.get("message") +
               "&orderId=" + response.get("orderId") + 
               "&orderInfo=" + response.get("orderInfo") + 
               "&partnerCode=" + response.get("partnerCode") + 
               "&payType=" + response.get("payType") + 
               "&requestId=" + response.get("requestId") + 
               "&responseTime=" + response.get("responseTime") + 
               "&resultCode=" + response.get("resultCode") + 
               "&transId=" + response.get("transId");
    }
}