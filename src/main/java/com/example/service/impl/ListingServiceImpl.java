package com.example.service;

import com.example.dao.ListingDAO;
import com.example.model.Admin;
import com.example.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingServiceImpl {

    @Autowired
    private ListingDAO listingDAO;

    public List<Listing> getAllListings() {
        return listingDAO.findAll();
    }

    public Listing getListingById(Long id) {
        return listingDAO.findById(id);
    }

    public Listing createListing(Listing listing) {
        if (listing.getStatus() == null || listing.getStatus().isEmpty()) {
            listing.setStatus("Pending");
        }
        return listingDAO.save(listing);
    }

    public Listing updateListing(Listing listing) {
        if (listing.getId() == null) {
            throw new IllegalArgumentException("Listing ID cannot be null for update.");
        }
        return listingDAO.save(listing);
    }

    public void deleteListing(Long id) {
        listingDAO.delete(id);
    }

    public List<Listing> searchListing(String keyword) {
        return listingDAO.searchByDescription(keyword);
    }

    public void approveListing(Long id, Admin admin) {
        Listing listing = listingDAO.findById(id);
        if (listing != null) {
            listing.setApprovedBy(admin);
            listing.setStatus("Approved");
            listingDAO.save(listing);
        } else {
            throw new RuntimeException("Listing not found with id: " + id);
        }
    }

    public List<Listing> getListingsByStatus(String status) {
        return listingDAO.findByStatus(status);
    }
}
