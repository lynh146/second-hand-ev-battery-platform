package com.example.controller;

import com.example.model.User;
import com.example.model.Wallet;
import com.example.repository.UserRepository;
import com.example.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WalletPageController {

    private final IWalletService walletService;
    private final UserRepository userRepository;

    @GetMapping("/member/wallet")
    public String myWallet(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found: " + email);
        }

        Wallet wallet = walletService.getOrCreateWallet(user.getUserID());

        model.addAttribute("wallet", wallet);
        model.addAttribute("user", user);

        return "wallet";  
    }
}
