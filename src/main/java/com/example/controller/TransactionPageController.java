package com.example.controller;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionPageController {

    private final ITransactionService transactionService;
    private final UserRepository userRepository;

    @GetMapping("/member/transactions")
    public String myTransactions(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found: " + email);
        }

        List<Transaction> list =
                transactionService.getTransactionsByBuyer(user.getUserID());

        model.addAttribute("transactions", list);
        return "transactions";
    }
}
