package com.example.service.impl;

import com.example.dao.ListingDAO;
import com.example.model.Listing;
import com.example.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingDAO listingDAO;

    @Override
    public Listing create(Listing listing) {
        return listingDAO.createListing(listing);
    }

    @Override
    public List<Listing> findAll() {
        return listingDAO.getAllListings();
    }
    @Override
    public Listing findById(Long id) {
        return listingDAO.getListingById(id).orElse(null);
}

}
