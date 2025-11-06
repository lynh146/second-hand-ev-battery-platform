package com.example.evplatform.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String method;
    private String status;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    private LocalDateTime createdAt = LocalDateTime.now();
}
