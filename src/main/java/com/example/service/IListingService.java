package com.example.service;

import java.util.List;

import com.example.model.Admin;
import com.example.model.Listing;

public interface IListingService {

    Listing createListing(Listing listing);


    List<Listing> findAll();


    Listing findById(Long id);
}