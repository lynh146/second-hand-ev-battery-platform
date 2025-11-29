package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "WALLET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WalletID")
    private Long walletID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "Balance", precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "Status")
    private String status;
}
