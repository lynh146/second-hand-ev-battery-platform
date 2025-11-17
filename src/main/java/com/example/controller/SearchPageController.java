package com.example.controller;

import com.example.model.Listing;
import com.example.model.Review;
import com.example.service.IListingService;
import com.example.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchPageController {

    private final IListingService listingService;
    private final IReviewService reviewService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<Listing> featured = listingService.featured(12);
        model.addAttribute("featuredListings", featured);
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(required = false) String keyword, Model model) {
        List<Listing> results = listingService.searchListing(keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("results", results);
        return "search";
    }

    @GetMapping("/listings/{id}")
    public String listingDetail(@PathVariable Long id,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {

        Listing listing = listingService.getPublicDetail(id);
        if (listing == null) {
            return "error/404";
        }

        Page<Review> reviewsPage = reviewService.getReviewsByListing(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        model.addAttribute("listing", listing);
        model.addAttribute("reviewsPage", reviewsPage);

        return "listing_public";
    }
}
