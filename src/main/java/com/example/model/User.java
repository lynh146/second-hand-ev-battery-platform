package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String role; // Buyer, Seller, Admin, etc.
    private LocalDateTime registerDate;
    private String status;

    // --- Relationships ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wallet> wallets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
