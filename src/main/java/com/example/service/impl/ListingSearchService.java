package com.example.service.impl;

import com.example.model.Admin;
import com.example.model.Listing;
import com.example.repository.ListingRepository;
import com.example.service.IListingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class ListingSearchService implements IListingService {
    
    private final ListingRepository repo;

    @Override
    public void createListing(Listing l) {
        throw new UnsupportedOperationException("Not in Search module");
    }

    @Override
    public void approveListing(Long id, Admin a) {
        throw new UnsupportedOperationException("Not in Search module");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listing> searchListing(String keyword) {
        String k = (keyword == null) ? "" : keyword;
        return repo.findByApprovedTrueAndStatusAndTitleContainingIgnoreCase("PUBLIC", k);
    }

}
