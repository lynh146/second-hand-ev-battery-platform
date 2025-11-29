package com.example.controller;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/transactions")
public class TransactionDetailController {

    private final ITransactionService txService;
    private final UserRepository userRepo;

    @GetMapping("/{txId}")
    public String detail(
            @PathVariable Long txId,
            Principal principal,
            Model model
    ) {

        if (principal == null) return "redirect:/login";

        User user = userRepo.findByEmail(principal.getName());
        if (user == null) return "redirect:/login";

        Transaction tx = txService.getTransactionById(txId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!tx.getBuyer().getUserID().equals(user.getUserID())) {
            return "redirect:/";
        }

        model.addAttribute("tx", tx);
        return "transaction_detail";
    }
}
