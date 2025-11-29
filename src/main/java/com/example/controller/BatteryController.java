package com.example.controller;

import com.example.model.Listing;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/batteries")
public class BatteryController {

    private final UserRepository userRepository;
    private final IListingService listingService;

    @GetMapping("/my")
    public String myBatteryListings(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        List<Listing> list = listingService
                .findAll()
                .stream()
                .filter(l -> l.getUser().getUserID().equals(user.getUserID()))
                .filter(l -> l.getBattery() != null)
                .toList();

        model.addAttribute("listings", list);
        return "my_batteries";
    }
}
