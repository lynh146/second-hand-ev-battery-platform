package com.example.model;

<<<<<<< HEAD
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

=======
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEW")
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@Entity
@Table(name = "REVIEW")
=======
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewID")
    private Long reviewID;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ListingID")
    private Listing listing;

    @Column(name = "Rating")
    private int rating; // ví dụ: 1-5 sao
=======
    @JoinColumn(name = "ReviewerID", nullable = false)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "TransactionID", nullable = false)
    private Transaction transaction;

    @Column(name = "Rating")
    private int rating;
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c

    @Column(name = "Comment", length = 1000)
    private String comment;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
}
