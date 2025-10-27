package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    private BigDecimal amount;
    private String method;
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
}

