package com.example.controller;

import com.example.model.Listing;
import com.example.service.IListingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/compare")
public class CompareController {

    private final IListingService listingService;

    
    @PostMapping("/add")
    public String addToCompare(@RequestParam Long listingId, HttpSession session) {

        List<Long> compareList = (List<Long>) session.getAttribute("compareList");
        if (compareList == null) {
            compareList = new ArrayList<>();
        }

        if (!compareList.contains(listingId)) {
            if (compareList.size() < 3) {
                compareList.add(listingId);
            }
        }

        session.setAttribute("compareList", compareList);

        return "redirect:/compare/view";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {
        List<Long> compareList = (List<Long>) session.getAttribute("compareList");

        if (compareList != null) {
            compareList.remove(id);
            session.setAttribute("compareList", compareList);
        }
        return "redirect:/compare/view";
    }

    @GetMapping("/clear")
    public String clear(HttpSession session) {
        session.removeAttribute("compareList");
        return "redirect:/compare/view";
    }

    @GetMapping("/view")
    public String view(Model model, HttpSession session) {

        List<Long> compareList = (List<Long>) session.getAttribute("compareList");
        List<Listing> listings = new ArrayList<>();

        if (compareList != null) {
            for (Long id : compareList) {
                Listing l = listingService.findById(id);
                if (l != null) listings.add(l);
            }
        }

        model.addAttribute("items", listings);
        return "compare_view";
    }
}
