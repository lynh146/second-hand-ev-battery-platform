// Module: 2. Listing Manager (Trần Đoàn Tiến Đạt)
package com.example.controller;

import com.example.model.Listing;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/member/listings")
@RequiredArgsConstructor
public class ListingController {

    private final IListingService listingService;

    @PostMapping
    public ResponseEntity<Listing> create(@RequestBody Listing listing) {
        Listing created = listingService.createListing(listing);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<?> uploadImages(
            @PathVariable Long id,
            @RequestParam("images") List<MultipartFile> files) {

        listingService.uploadImages(id, files);
        return ResponseEntity.ok("Uploaded");
    }

    @GetMapping
    public ResponseEntity<List<Listing>> getAll() {
        return ResponseEntity.ok(listingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> detail(@PathVariable Long id) {
        return ResponseEntity.ok(listingService.findById(id));
    }
}
