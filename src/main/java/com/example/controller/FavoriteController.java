package com.example.controller;

import com.example.model.Favorite;
import com.example.model.Listing;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.IFavoriteService;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final IFavoriteService favoriteService;
    private final IListingService listingService;
    private final UserRepository userRepository;

    @PostMapping("/toggle/{listingId}")
    public ResponseEntity<?> toggleFavorite(@PathVariable Long listingId, Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Bạn phải đăng nhập!"
            ));
        }

        User user = userRepository.findByEmail(principal.getName());
        Listing listing = listingService.findById(listingId);

        if (listing == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Listing không tồn tại!"
            ));
        }

        boolean saved = favoriteService.toggleFavorite(user, listing);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "saved", saved
        ));
    }

    @GetMapping("/check/{listingId}")
    public ResponseEntity<?> checkFavorite(@PathVariable Long listingId, Principal principal) {

        if (principal == null) {
            return ResponseEntity.ok(Map.of("saved", false));
        }

        User user = userRepository.findByEmail(principal.getName());
        Listing listing = listingService.findById(listingId);

        boolean saved = favoriteService.isFavorite(user, listing);

        return ResponseEntity.ok(Map.of("saved", saved));
    }

    @GetMapping("/my")
    public ResponseEntity<?> myFavorites(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Bạn chưa đăng nhập"
            ));
        }

        User user = userRepository.findByEmail(principal.getName());
        List<Favorite> favorites = favoriteService.getUserFavorites(user);

        List<Map<String, Object>> result = favorites.stream().map(f -> {
            Listing l = f.getListing();

            String thumb = "/images/no-image.png";
            if (l.getImages() != null && !l.getImages().isEmpty()) {
                thumb = l.getImages().get(0).getImageUrl();
            }

            Map<String, Object> m = new HashMap<>();
            m.put("listingID", l.getListingID());
            m.put("title", l.getTitle());
            m.put("price", l.getPrice());
            m.put("thumbnail", thumb);
            m.put("ownerName", l.getUser().getFullName());

            return m;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
