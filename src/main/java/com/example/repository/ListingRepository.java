package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Listing;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
