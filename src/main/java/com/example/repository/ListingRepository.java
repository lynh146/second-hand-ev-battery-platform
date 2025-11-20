package com.example.repository;

import com.example.model.Listing;
import com.example.model.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(String status);

    List<Listing> findByDescriptionContainingIgnoreCase(String keyword);

    List<Listing> findByApprovedBy(Admin admin);

    List<Listing> findByApprovedTrueAndStatusAndTitleContainingIgnoreCase(String status, String keyword);

    Page<Listing> findByApprovedTrueAndStatus(String status, org.springframework.data.domain.Pageable pageable);
}

