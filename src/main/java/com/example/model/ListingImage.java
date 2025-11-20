package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LISTING_IMAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ListingImageID")
    private Long listingImageID;

    @ManyToOne
    @JoinColumn(name = "ListingID", nullable = false)
    private Listing listing;

    @Column(name = "ImageURL")
    private String imageUrl;

    @Column(name = "SortOrder")
    private Integer sortOrder;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
}
