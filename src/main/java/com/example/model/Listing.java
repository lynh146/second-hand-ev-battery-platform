package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Listing")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicleID")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "batteryID")
    private Battery battery;

    @Column(length = 500)
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private LocalDateTime createdAt;
    private String status;

    @ManyToOne
    @JoinColumn(name = "approvedBy")
    private Admin approvedBy;
}
