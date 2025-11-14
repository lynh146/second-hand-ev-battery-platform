package com.example.dao;
import com.example.model.Admin;
import com.example.model.Listing;
import com.example.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ListingDAO {

    @Autowired
    private ListingRepository listingRepository;

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public Optional<Listing> getListingById(Long listingID) {
        return listingRepository.findById(listingID);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing updateListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public void deleteListingById(Long listingID) {
        listingRepository.deleteById(listingID);
    }


    public void deleteListing(Listing listing) {
        listingRepository.delete(listing);
    }


    public List<Listing> filterByStatus(String status) {
        return listingRepository.findByStatus(status);
    }


    public List<Listing> searchByKeyword(String keyword) {
        return listingRepository.findByDescriptionContainingIgnoreCase(keyword);
    }


    public List<Listing> filterByApprovedAdmin(Admin admin) {
        return listingRepository.findByApprovedBy(admin);
    }


    public List<Listing> searchListingsByDescription(String keyword) {
        return listingRepository.findByDescriptionContainingIgnoreCase(keyword);
    }

    public List<Listing> searchApprovedListings(String status, String keyword) {
        return listingRepository.findByApprovedTrueAndStatusAndTitleContainingIgnoreCase(status, keyword);
    }

    public boolean existsById(Long listingID) {
        return listingRepository.existsById(listingID);
    }

    public long countTotalListings() {
        return listingRepository.count();
    }

    public long countListingsByStatus(String status) {
        return listingRepository.findByStatus(status).size();
    }
}
