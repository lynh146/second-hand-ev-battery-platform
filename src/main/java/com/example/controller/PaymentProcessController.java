 package com.example.controller;

import com.example.service.IPaymentService;
import com.example.service.ITransactionService;
import com.example.service.MoMoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class PaymentProcessController {

    private final ITransactionService transactionService;
    private final IPaymentService paymentService;
    private final MoMoService moMoService;

     
    @PostMapping("/payment/confirm")
    public String confirmPayment(
            @RequestParam Long listingId,
            @RequestParam BigDecimal totalAmount,
            @RequestParam String paymentMethod
    ) {
       
        if (!"MOMO".equalsIgnoreCase(paymentMethod)) {
            return "redirect:/payment/status?orderId=0&result=ERROR&message=Method_Not_Supported";
        }

        try {
             
            Long buyerId = 1L;

            
            Long transactionId = transactionService.createTransaction(listingId, buyerId, totalAmount);

             
            Long paymentId = paymentService.createPaymentRecord(
                    transactionId,
                    totalAmount,
                    paymentMethod
            );

             
            long momoAmount = totalAmount.longValue();

            String payUrl = moMoService.createPaymentRequest(
                    momoAmount,
                    "Thanh toan giao dich " + transactionId,
                    paymentId
            );

            return "redirect:" + payUrl;

        } catch (Exception e) {
            return "redirect:/payment/status?orderId=0&result=ERROR&message=" + e.getMessage();
        }
    }
}
