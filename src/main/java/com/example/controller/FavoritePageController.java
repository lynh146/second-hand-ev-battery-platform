package com.example.controller;

import com.example.model.Listing;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.IFavoriteService;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/favorites")
public class FavoritePageController {

    private final IFavoriteService favoriteService;
    private final IListingService listingService;
    private final UserRepository userRepository;

    @GetMapping
    public String myFavorites(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName());

        var list = favoriteService.getUserFavorites(user);
        model.addAttribute("favorites", list);

        return "favorite_list";
    }

    // xóa ở trang sản phẩm 
    @PostMapping("/toggle/{listingId}")
    public String toggleFavorite(@PathVariable Long listingId, Principal principal) {

        if (principal == null) return "redirect:/login";

        User user = userRepository.findByEmail(principal.getName());
        Listing listing = listingService.findById(listingId);

        boolean saved = favoriteService.toggleFavorite(user, listing);

        return "redirect:/listings/" + listingId + "?saved=" + saved;
    }

    // xóa ở trang danh sách yêu thích
    @PostMapping("/remove/{id}")
    public String removeFavorite(@PathVariable Long id, Principal principal) {

        if (principal == null) return "redirect:/login";

        User user = userRepository.findByEmail(principal.getName());
        Listing listing = listingService.findById(id);

        favoriteService.toggleFavorite(user, listing);

        return "redirect:/member/favorites";
    }
}
