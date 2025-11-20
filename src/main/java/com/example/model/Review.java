package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEW")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewID")
    private Long reviewID;

    @ManyToOne
    @JoinColumn(name = "ReviewerID", nullable = false)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "TransactionID", nullable = false)
    private Transaction transaction;

    @Column(name = "Rating")
    private int rating;

    @Column(name = "Comment", length = 1000)
    private String comment;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
}
