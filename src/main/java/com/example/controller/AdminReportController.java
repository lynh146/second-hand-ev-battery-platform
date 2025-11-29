package com.example.controller;

import com.example.model.Payment;
import com.example.repository.ListingRepository;
import com.example.repository.PaymentRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/report")
public class AdminReportController {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;

    @GetMapping
    public String reportPage(Model model) {

        long totalUsers      = userRepository.count();
        long totalListings   = listingRepository.count();
        long pendingListings = listingRepository.findByStatus("PENDING").size();
        long publicListings  = listingRepository.findByStatus("PUBLIC").size();

        long totalTransactions   = transactionRepository.count();
        long successTransactions = transactionRepository.countByStatus("SUCCESS");

        List<Payment> successPayments = paymentRepository.findByStatus("SUCCESS");

        BigDecimal totalRevenue = successPayments.stream()
                .map(Payment::getAmount)
                .filter(a -> a != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCommission = successPayments.stream()
                .map(Payment::getCommissionFee)
                .filter(a -> a != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalListings", totalListings);
        model.addAttribute("pendingListings", pendingListings);
        model.addAttribute("publicListings", publicListings);
        model.addAttribute("totalTransactions", totalTransactions);
        model.addAttribute("successTransactions", successTransactions);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalCommission", totalCommission);

        return "admin_report";
    }
}
