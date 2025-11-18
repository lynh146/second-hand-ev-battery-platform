<<<<<<< HEAD
=======
package com.example.dao;

import com.example.model.Listing;
import com.example.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ListingDAO {

    private final ListingRepository listingRepository;
    
    public Listing create(Listing listing) {
        return listingRepository.save(listing);
    }

    public Optional<Listing> findById(Long id) {
        return listingRepository.findById(id);
    }

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    public void deleteById(Long id) {
        listingRepository.deleteById(id);
    }

}
>>>>>>> 8dd7c02 (Update ListingController and ListingDAO)
