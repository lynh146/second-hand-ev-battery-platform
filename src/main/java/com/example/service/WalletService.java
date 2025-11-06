package com.example.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.model.Wallet;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public Wallet createWallet(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy userId: " + userId));

        if (walletRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("User này đã có ví rồi!");
        }

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .status("ACTIVE")
                .build();

        return walletRepository.save(wallet);
    }

    public Wallet getWalletByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy userId: " + userId));

        return walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User chưa có ví!"));
    }

    public Wallet deposit(Long userId, BigDecimal amount) {
        Wallet wallet = getWalletByUser(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }

    public Wallet withdraw(Long userId, BigDecimal amount) {
        Wallet wallet = getWalletByUser(userId);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Số dư không đủ để rút tiền!");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletRepository.save(wallet);
    }
}
