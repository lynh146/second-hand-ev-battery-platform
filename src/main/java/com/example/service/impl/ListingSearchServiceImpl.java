package com.example.service.impl;

import com.example.model.Admin;
import com.example.model.Listing;
import com.example.repository.ListingRepository;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ListingSearchServiceImpl implements IListingService {

    private final ListingRepository repo;

    @Override
    public Listing createListing(Listing l) {
        throw new UnsupportedOperationException("Not in Search module");
    }

    @Override
    public Listing approveListing(Long id, Admin a) {
        throw new UnsupportedOperationException("Not in Search module");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listing> searchListing(String keyword) {
        String k = (keyword == null) ? "" : keyword.trim();
        return repo.findByStatusAndTitleContainingIgnoreCase("PUBLIC", k);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listing> featured(int limit) {
        var p = org.springframework.data.domain.PageRequest.of(
                0,
                Math.max(1, limit),
                org.springframework.data.domain.Sort.by(
                        org.springframework.data.domain.Sort.Direction.DESC,
                        "createdAt"
                )
        );
        return repo.findByStatusOrderByCreatedAtDesc("PUBLIC", p).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Listing getPublicDetail(Long id) {
        return repo.findByListingIDAndStatus(id, "PUBLIC");
    }

    @Override
    public List<Listing> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Listing findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}
