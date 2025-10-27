package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    @ManyToOne
    @JoinColumn(name = "listingID")
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "buyerID")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "paymentID")
    private Payment payment;

    private LocalDateTime transactionDate;
}

