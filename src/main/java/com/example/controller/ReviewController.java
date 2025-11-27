package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.service.IReviewService;
import com.example.service.ITransactionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;
    private final ITransactionService transactionService;

    @PostMapping("/transaction/{txId}")
    public String submitReview(
            @PathVariable Long txId,
            @RequestParam int rating,
            @RequestParam String comment
    ) {
        User currentUser = null;

        Transaction tx = transactionService.getTransactionById(txId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        reviewService.writeReview(currentUser, tx, comment, rating);

        return "redirect:/transaction/detail?id=" + txId;
    }
}
