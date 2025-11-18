package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "LISTING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ListingID")
    private Long listingID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;    

    @ManyToOne
    @JoinColumn(name = "VehicleID")
    private Vehicle vehicle;  

    @ManyToOne
    @JoinColumn(name = "BatteryID")
    private Battery battery;   

    @Column(name = "Title")
    private String title;

    @Column(name = "Description", length = 500)
    private String description;

    @Column(name = "Price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ApprovedBy")
    private Admin approvedBy;

    @OneToMany(mappedBy = "listing",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<ListingImage> images;
}
