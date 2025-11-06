package com.example.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Wallet;
import com.example.service.WalletService;

@RestController
@RequestMapping("/wallets")
@CrossOrigin(origins = "*")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create/{userId}")
    public Wallet createWallet(@PathVariable Long userId) {
        return walletService.createWallet(userId);
    }

    @GetMapping("/{userId}")
    public Wallet getWalletByUser(@PathVariable Long userId) {
        return walletService.getWalletByUser(userId);
    }

    @PostMapping("/deposit")
    public Wallet deposit(@RequestParam Long userId, @RequestParam BigDecimal amount) {
        return walletService.deposit(userId, amount);
    }

    @PostMapping("/withdraw")
    public Wallet withdraw(@RequestParam Long userId, @RequestParam BigDecimal amount) {
        return walletService.withdraw(userId, amount);
    }
}
