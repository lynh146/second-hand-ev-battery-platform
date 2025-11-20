package com.example.service.impl;

import com.example.model.Admin;
import com.example.model.Listing;
import com.example.repository.ListingRepository;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary 
@RequiredArgsConstructor
@Transactional
public class ListingServiceImpl implements IListingService {

    private final ListingRepository listingRepository;

    @Override
    public Listing createListing(Listing listing) {
        listing.setCreatedAt(LocalDateTime.now());
        if (listing.getStatus() == null) {
            listing.setStatus("PENDING");
        }
        return listingRepository.save(listing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Listing findById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id = " + id));
    }
