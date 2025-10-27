package com.example.service;

import java.util.List;

import com.example.model.Listing;

public interface ListingService {
    Listing create(Listing listing);
    List<Listing> findAll();
    Listing findById(Long id);
}
