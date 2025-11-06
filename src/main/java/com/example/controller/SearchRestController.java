package com.example.controller;

import java.util.List;
import com.example.model.Listing;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class SearchRestController {
private final IListingService listingService;

  @GetMapping("/search/simple")
  public List<Listing> search(@RequestParam String keyword) {
    return listingService.searchListing(keyword);
  }

  @GetMapping("/featured")
  public List<Listing> featured(@RequestParam(defaultValue="12") int limit) {
    return listingService.searchListing("").stream().limit(limit).toList();
  }

}
