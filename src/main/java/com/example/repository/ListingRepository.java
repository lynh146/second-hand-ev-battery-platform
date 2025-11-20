package com.example.repository;

import com.example.model.Listing;
import com.example.model.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(String status);

    List<Listing> findByStatusAndTitleContainingIgnoreCase(String status, String keyword);

    Page<Listing> findByStatus(String status, Pageable pageable);

    Listing findByListingIDAndStatus(Long listingID, String status);

    List<Listing> findByApprovedBy(Admin admin);

    Page<Listing> findByApprovedTrueAndStatus(String status, org.springframework.data.domain.Pageable pageable);
}

