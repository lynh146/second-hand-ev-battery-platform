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

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    public Optional<Listing> findById(Long id) {
        return listingRepository.findById(id);
    }

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    public void delete(Long id) {
        listingRepository.deleteById(id);
    }

    public List<Listing> searchByKeyword(String keyword) {
        return listingRepository.findByDescriptionContainingIgnoreCase(keyword);
    }

    public List<Listing> findByStatus(String status) {
        return listingRepository.findByStatus(status);
    }

    public List<Listing> findByApprovedBy(Admin admin) {
        return listingRepository.findByApprovedBy(admin);
    }
}
