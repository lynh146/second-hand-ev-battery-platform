package com.example.service;

import com.example.model.Wallet;

import java.math.BigDecimal;

public interface IWalletService {

    Wallet getOrCreateWallet(Long userId);

    Wallet getWalletByUser(Long userId);

    Wallet deposit(Long userId, BigDecimal amount);

    Wallet withdraw(Long userId, BigDecimal amount);
}
