package com.example.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.IReviewService;
import com.example.service.ITransactionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;
    private final ITransactionService txService;
    private final UserRepository userRepo;

    @GetMapping("/form/{txId}")
    public String reviewForm(
            @PathVariable Long txId,
            Principal principal,
            Model model) {

        User user = userRepo.findByEmail(principal.getName());
        Transaction tx = txService.getTransactionById(txId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!tx.getBuyer().getUserID().equals(user.getUserID())) {
            return "redirect:/member/transactions";
        }

        if (!reviewService.canReview(txId, user.getUserID())) {
            return "redirect:/member/reviews";
        }

        model.addAttribute("tx", tx);
        return "review_form";
    }


    @PostMapping("/transaction/{txId}")
    public String submitReview(
            @PathVariable Long txId,
            @RequestParam int rating,
            @RequestParam String comment,
            Principal principal) {

        User reviewer = userRepo.findByEmail(principal.getName());

        reviewService.writeReview(txId, reviewer.getUserID(), rating, comment);

        return "redirect:/member/reviews";
    }


    @GetMapping
    public String reviewCenter(@RequestParam(defaultValue = "received") String tab,
                            Model model,
                            Principal principal) {

        User user = userRepo.findByEmail(principal.getName());
        Long uid = user.getUserID();

        model.addAttribute("tab", tab);

        model.addAttribute("receivedReviews", reviewService.getReviewsOfUser(uid));
        model.addAttribute("writtenReviews", reviewService.getReviewsWritten(uid));
        model.addAttribute("pendingReviews", reviewService.getPendingReviews(uid));

        return "review_center";
    }

}
