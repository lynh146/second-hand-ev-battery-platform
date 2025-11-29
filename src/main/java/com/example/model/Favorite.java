package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "FAVORITE")
@Data
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteID")
    private Long favoriteId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ListingID")
    private Listing listing;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}

