package com.example.controller;

import com.example.model.Listing;
import com.example.repository.ListingRepository;
import com.example.service.IPaymentService;
import com.example.service.ITransactionService;
import com.example.service.IWalletService;
import com.example.service.MomoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PaymentProcessController {

    private final ListingRepository listingRepository;
    private final ITransactionService transactionService;
    private final IPaymentService paymentService;
    private final MomoService momoService;
    private final IWalletService walletService;

    private static final BigDecimal PLATFORM_FEE_RATE = BigDecimal.valueOf(0.05);

    @GetMapping("/payment/checkout/{listingId}")
    public String showCheckout(@PathVariable Long listingId,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Listing listing = listingRepository.findById(listingId).orElse(null);

        if (listing == null) {
            redirectAttributes.addFlashAttribute("error",
                    "Tin đăng không tồn tại hoặc đã bị xóa.");
            return "redirect:/";
        }

        BigDecimal price = listing.getPrice() != null ? listing.getPrice() : BigDecimal.ZERO;
        BigDecimal fee   = price.multiply(PLATFORM_FEE_RATE);
        BigDecimal total = price.add(fee);

        model.addAttribute("item", listing);
        model.addAttribute("fee", fee);
        model.addAttribute("total", total);

        return "payment"; 
    }

    @PostMapping("/payment/confirm")
    public String confirmPayment(@RequestParam Long listingId,
                                 @RequestParam Long buyerId,
                                 @RequestParam BigDecimal totalAmount,
                                 @RequestParam String paymentMethod) {

        if (!"MOMO".equalsIgnoreCase(paymentMethod)) {
            String msg = urlEncode("Phương thức thanh toán chưa hỗ trợ.");
            return "redirect:/payment/error?message=" + msg;
        }

        try {
            Long transactionId = transactionService.createTransaction(
                    listingId, buyerId, totalAmount
            );

            Long paymentId = paymentService.createPaymentRecord(
                    transactionId, totalAmount, "MOMO"
            );

            long momoAmount = totalAmount.longValue();
            String orderInfo = "Thanh toán giao dịch " + transactionId;

            String payUrl = momoService.createPaymentRequest(
                    momoAmount, orderInfo, paymentId   
            );

            if (payUrl == null) {
                String msg = urlEncode("Không nhận được link thanh toán từ MoMo.");
                return "redirect:/payment/error?message=" + msg;
            }

            return "redirect:" + payUrl;

        } catch (Exception e) {
            e.printStackTrace();
            String msg = urlEncode("Có lỗi khi xử lý thanh toán: " + e.getMessage());
            return "redirect:/payment/error?message=" + msg;
        }
    }

    @GetMapping("/pay/momo/return")
    public String handleMomoReturn(@RequestParam Map<String, String> params,
                                   Model model) {

        System.out.println("MoMo RETURN params = " + params);

        String resultCode = params.get("resultCode"); 
        String orderId    = params.get("orderId");   
        if (resultCode == null) {
            model.addAttribute("message", "Không nhận được mã kết quả từ MoMo.");
            return "payment_error";
        }

        try {
            if ("0".equals(resultCode)) {
                paymentService.processMomoSuccess(orderId, new HashMap<>(params));

                model.addAttribute("paymentId", orderId);
                model.addAttribute("message", "Thanh toán thành công.");
                return "payment_return";
            } else {
                paymentService.processMomoFailure(orderId, new HashMap<>(params));

                model.addAttribute("message",
                        "Thanh toán thất bại. Mã lỗi từ MoMo: " + resultCode);
                return "payment_error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message",
                    "Có lỗi khi cập nhật trạng thái thanh toán: " + e.getMessage());
            return "payment_error";
        }
    }

    @GetMapping("/payment/error")
    public String paymentError(@RequestParam(value = "message", required = false) String message,
                               Model model) {
        model.addAttribute("message",
                message != null ? message : "Có lỗi xảy ra trong quá trình thanh toán.");
        return "payment_error";
    }

    private String urlEncode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
