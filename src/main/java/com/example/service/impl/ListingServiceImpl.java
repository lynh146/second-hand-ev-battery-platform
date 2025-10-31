package com.example.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Listing;
import com.example.repository.ListingRepository;
import com.example.service.ListingService;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;

    public ListingServiceImpl(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Override
    public Listing create(Listing listing) {
        return listingRepository.save(listing);
    }

    @Override
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    public Listing findById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }
}
