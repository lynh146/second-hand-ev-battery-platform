package com.example.controller;

import com.example.model.Transaction;
import com.example.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final ITransactionService transactionService; 
    private static final BigDecimal PLATFORM_FEE_RATE = BigDecimal.valueOf(0.05);

    
    private static class ListingMock {
        private Long listingID = 1L; private String name = "Xe điện PinFast VF-X";
        private BigDecimal price = new BigDecimal("15000000.00");
        public Long getListingID() { return listingID; } public String getName() { return name; } public BigDecimal getPrice() { return price; }
        public void setListingID(Long id) { this.listingID = id; }
    }

     
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
         
        return "payment.html"; 
    }
    
     
    @GetMapping("/transaction/history")
    public String showTransactionHistory(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions(); 
        model.addAttribute("transactions", transactions);
        // SỬA: TRẢ VỀ TÊN FILE CÓ HẬU TỐ CHÍNH XÁC
        return "transaction_history.html"; 
    }
    
    // 3. Hiển thị Chi tiết Giao dịch (transaction_detail.htm)
    @GetMapping("/transaction/detail")
    public String showTransactionDetail(@RequestParam Long id, Model model) {
        Optional<Transaction> optionalTx = transactionService.getTransactionById(id);
        
        if (optionalTx.isEmpty()) {
             return "error-404";
        }
        model.addAttribute("transaction", optionalTx.get());
         
        return "transaction_detail.html"; 
    }

  
    @GetMapping("/payment/status")
    public String paymentStatus(@RequestParam String orderId, 
                                @RequestParam String result, 
                                Model model) {
        
        model.addAttribute("paymentId", orderId);
        model.addAttribute("result", result.toUpperCase());
        
        return "payment-status.html"; 
    }
}