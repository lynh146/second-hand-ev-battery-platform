package com.example.controller;

import com.example.model.Listing;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member/listings")
@RequiredArgsConstructor
public class ListingController {

    private final IListingService listingService;

    @PostMapping
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        Listing created = listingService.createListing(listing);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        return ResponseEntity.ok(listingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        return ResponseEntity.ok(listingService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id,
                                                 @RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.updateListing(id, listing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
