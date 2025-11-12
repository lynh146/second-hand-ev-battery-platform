package com.example.service;

import java.util.List;
import com.example.model.Admin;
import com.example.model.Listing;

public interface IListingService {
    void createListing(Listing l);
    void approveListing(Long id, Admin a);
    List<Listing> searchListing(String keyword); 
    List<Listing> featured(int limit);
}
