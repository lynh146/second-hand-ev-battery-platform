package com.example.service.impl;

import com.example.model.User;
import com.example.model.Wallet;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;
import com.example.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements IWalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public Wallet getOrCreateWallet(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        return walletRepository.findByUser(user)
                .orElseGet(() -> {
                    Wallet w = Wallet.builder()
                            .user(user)
                            .balance(BigDecimal.ZERO)
                            .createdAt(LocalDateTime.now())
                            .status("ACTIVE")
                            .build();
                    return walletRepository.save(w);
                });
    }

    @Override
    public Wallet getWalletByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return walletRepository.findByUser(user).orElse(null);
    }

    @Override
    public Wallet deposit(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Số tiền nạp phải > 0");
        }
        Wallet wallet = getOrCreateWallet(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet withdraw(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Số tiền rút phải > 0");
        }
        Wallet wallet = getOrCreateWallet(userId);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Số dư không đủ");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletRepository.save(wallet);
    }
}
