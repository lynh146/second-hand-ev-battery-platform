package com.example.service.impl;

import com.example.model.Admin;
import com.example.model.Listing;
import com.example.repository.ListingRepository;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class ListingServiceImpl implements IListingService {

    private final ListingRepository listingRepository;
    @Override
    public Listing createListing(Listing listing) {
        if (listing.getCreatedAt() == null) {
            listing.setCreatedAt(LocalDateTime.now());
        }
        if (listing.getStatus() == null) {
            listing.setStatus("PENDING");
        }
        return listingRepository.save(listing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Listing findById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found: " + id));
    }

    @Override
    public Listing updateListing(Long id, Listing updated) {
        Listing existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setVehicle(updated.getVehicle());
        existing.setBattery(updated.getBattery());
        existing.setStatus(updated.getStatus());
        return listingRepository.save(existing);
    }

    @Override
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    //Search & Guest 
    @Override
    @Transactional(readOnly = true)
    public List<Listing> searchListing(String keyword) {
        String k = (keyword == null) ? "" : keyword.trim();
        if (k.isEmpty()) {
            Pageable p = PageRequest.of(
                    0, 20,
                    Sort.by(Sort.Direction.DESC, "createdAt")
            );
            return listingRepository.findByStatus("PUBLIC", p).getContent();
        }
        return listingRepository.findByStatusAndTitleContainingIgnoreCase("PUBLIC", k);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listing> featured(int limit) {
        Pageable p = PageRequest.of(
                0,
                Math.max(1, limit),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return listingRepository.findByStatus("PUBLIC", p).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Listing getPublicDetail(Long id) {
        return listingRepository.findByListingIDAndStatus(id, "PUBLIC");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listing> getPendingListings() {
        // status = PENDING và chưa có admin duyệt
        return listingRepository.findByStatusAndApprovedByIsNull("PENDING");
    }

    @Override
    public Listing approveListing(Long id, Admin admin, boolean approved) {
        Listing listing = findById(id);
        if (approved) {
            listing.setStatus("PUBLIC");
            listing.setApprovedBy(admin);
        } else {
            listing.setStatus("REJECTED");
            listing.setApprovedBy(null);
        }
        return listingRepository.save(listing);
    }


}
