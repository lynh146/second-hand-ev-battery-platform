package com.example.controller;

import com.example.model.Transaction;
import com.example.service.ITransactionService; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/transactions")
@RequiredArgsConstructor
public class AdminTransactionController {

    private final ITransactionService transactionService;

    @GetMapping
    public String viewTransactions(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();

        model.addAttribute("transactions", transactions);
        model.addAttribute("activePage", "transactions");
        model.addAttribute("pageTitle", "Quản lý giao dịch");

        return "admin_transactions";
    }
}
