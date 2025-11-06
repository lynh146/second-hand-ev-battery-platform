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
        return listingDAO.save(listing);
    }

    @Override
    public List<Listing> findAll() {
        return listingDAO.findAll();
    }
    @Override
    public Listing findById(Long id) {
    return listingDAO.findById(id).orElse(null);
}

}
