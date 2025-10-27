package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
