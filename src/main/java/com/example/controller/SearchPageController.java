package com.example.controller;

import com.example.model.Listing;
import com.example.model.Review;
import com.example.service.IListingService;
import com.example.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public String searchPage(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String brand,
                            @RequestParam(required = false) Long minPrice,
                            @RequestParam(required = false) Long maxPrice,
                            Model model) {

        List<Listing> results = listingService.searchListing(keyword);

        if (category != null && !category.isBlank()) {
            switch (category) {
                case "vehicle" -> results = results.stream()
                        .filter(l -> l.getVehicle() != null)
                        .toList();
                case "battery" -> results = results.stream()
                        .filter(l -> l.getBattery() != null)
                        .toList();
            }
        }

        if (brand != null && !brand.isBlank()) {
            String brandLower = brand.toLowerCase();

            results = results.stream()
                    .filter(l ->
                            (l.getVehicle() != null &&
                            l.getVehicle().getBrand() != null &&
                            l.getVehicle().getBrand().toLowerCase().contains(brandLower))
                        ||
                            (l.getBattery() != null &&
                            l.getBattery().getBrand() != null &&
                            l.getBattery().getBrand().toLowerCase().contains(brandLower))
                    )
                    .toList();
        }

        if (minPrice != null) {
            results = results.stream()
                    .filter(l -> l.getPrice() != null &&
                                l.getPrice().longValue() >= minPrice)
                    .toList();
        }

        if (maxPrice != null) {
            results = results.stream()
                    .filter(l -> l.getPrice() != null &&
                                l.getPrice().longValue() <= maxPrice)
                    .toList();
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("brand", brand);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("results", results);

        return "search";
    }


    @GetMapping("/listings/{id}")
    public String listingDetail(@PathVariable Long id,
                                @RequestParam(required = false) Boolean saved,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {

        Listing listing = listingService.getPublicDetail(id);
        if (listing == null) {
            model.addAttribute("notFound", true);  
            return "listing_public";             
        }

        Page<Review> reviewsPage = reviewService.getReviewsByListing(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

    model.addAttribute("listing", listing);

    model.addAttribute("reviewsPage", reviewsPage);

    List<Listing> related = listingService.featured(12)
            .stream()
            .filter(l -> !l.getListingID().equals(id))
            .limit(8)
            .toList();

    model.addAttribute("relatedListings", related);

    model.addAttribute("saved", saved);

    return "listing_public";

        }
}
