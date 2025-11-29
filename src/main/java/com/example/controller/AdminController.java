package com.example.controller;

import com.example.repository.ListingRepository;
import com.example.repository.PaymentRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import com.example.service.impl.AdminServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;

@GetMapping("/dashboard")
public String dashboard(Model model) {

    model.addAttribute("activePage", "dashboard");
    model.addAttribute("pageTitle", "Admin Dashboard");

    long totalUsers = userRepository.count();
    long totalListings = listingRepository.count();
    long pendingListings = listingRepository.findByStatus("PENDING").size();
    long totalTransactions = transactionRepository.count();
    long successTransactions = transactionRepository.countByStatus("SUCCESS");

    model.addAttribute("totalUsers", totalUsers);
    model.addAttribute("totalListings", totalListings);
    model.addAttribute("pendingListings", pendingListings);
    model.addAttribute("totalTransactions", totalTransactions);
    model.addAttribute("successTransactions", successTransactions);

    return "admin_dashboard";
}

}
