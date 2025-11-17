package com.example.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Listing;
import com.example.model.Review;
import com.example.service.IListingService;
import com.example.service.IReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class SearchRestController {
  private final IListingService listingService;
  private final IReviewService reviewService;

  @GetMapping("/search/simple")
  public List<Listing> search(@RequestParam String keyword) {
    return listingService.searchListing(keyword);
  }

  @GetMapping("/featured")
  public List<Listing> featured(@RequestParam(defaultValue= "12") int limit) {
    return listingService.featured(limit);
  }

  @GetMapping("/listings/{id}")
  public ResponseEntity<Listing> getListingDetail(@PathVariable Long id) {
    Listing listing = listingService.getPublicDetail(id);
    if (listing == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(listing);
  }

  @GetMapping("/listings/{id}/reviews")
  public Page<Review> getListingReviews(
    @PathVariable Long id,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size
  ) {
    var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    return reviewService.getReviewsByListing(id, pageable);
  }
}
