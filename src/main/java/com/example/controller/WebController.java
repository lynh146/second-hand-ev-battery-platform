package com.example.controller;

import com.example.model.Transaction;
import com.example.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Bổ sung
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Bổ sung

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final ITransactionService transactionService; 
    private static final BigDecimal PLATFORM_FEE_RATE = BigDecimal.valueOf(0.05);
    // MOCK Class: Đại diện cho một sản phẩm/listing
    private static class ListingMock {
        private Long listingID = 1L; 
        private String name = "Xe điện PinFast VF-X";
        private BigDecimal price = new BigDecimal("15000000.00");

        public Long getListingID() { return listingID; } 
        public String getName() { return name; } 
        public BigDecimal getPrice() { return price; }
        
        public void setListingID(Long id) { this.listingID = id; }
        // Các setters khác (nếu cần)
    }

    // --- 1. HIỂN THỊ TRANG THANH TOÁN (GET) ---
    @GetMapping("/payment-page")
    public String showPaymentPage(@RequestParam Long listingId, Model model) {
        
        ListingMock item = new ListingMock(); 
        item.setListingID(listingId); 
        
        BigDecimal price = item.getPrice();
        BigDecimal fee = price.multiply(PLATFORM_FEE_RATE);
        BigDecimal total = price.add(fee);

        model.addAttribute("item", item);
        model.addAttribute("fee", fee);
        model.addAttribute("total", total);
        
        return "payment.htm"; 
    }
    
    // --- 2. XỬ LÝ THANH TOÁN HOÀN TẤT VÀ LƯU GIAO DỊCH (POST) ---
    @PostMapping("/payment/complete")
    public String handlePaymentComplete(@RequestParam String orderId,
                                       @RequestParam Long listingId,
                                       @RequestParam BigDecimal amount,
                                       RedirectAttributes redirectAttributes) {

        // Trong thực tế, bạn sẽ cần lấy thông tin chi tiết và xác thực từ cổng thanh toán.
        Transaction newTx = new Transaction();
        newTx.setOrderId(orderId);
        newTx.setListingId(listingId); 
        newTx.setAmount(amount);
        newTx.setStatus("COMPLETED"); 

        Transaction savedTx = transactionService.saveTransaction(newTx);
        
        // Chuyển hướng đến trang chi tiết giao dịch vừa tạo
        redirectAttributes.addAttribute("id", savedTx.getId());
        
        return "redirect:/transaction/detail";
    }

    // --- 3. XEM LỊCH SỬ GIAO DỊCH (GET) ---
    @GetMapping("/transaction/history")
    public String showTransactionHistory(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions(); 
        model.addAttribute("transactions", transactions);
        return "transaction_history.htm";
    }
    
    // --- 4. XEM CHI TIẾT GIAO DỊCH (GET) ---
    @GetMapping("/transaction/detail")
    public String showTransactionDetail(@RequestParam Long id, Model model) {
        Optional<Transaction> optionalTx = transactionService.getTransactionById(id);
        
        if (optionalTx.isEmpty()) {
             return "error-404";
        }
        model.addAttribute("transaction", optionalTx.get());
        return "transaction_detail.htm"; 
    }

    // --- 5. HIỂN THỊ TRẠNG THÁI THANH TOÁN (GET) ---
    @GetMapping("/payment/status")
    public String paymentStatus(@RequestParam String orderId, 
                                @RequestParam String result, 
                                Model model) {
        
        model.addAttribute("paymentId", orderId);
        model.addAttribute("result", result.toUpperCase());
        
        return "payment-status.htm"; 
    }
}