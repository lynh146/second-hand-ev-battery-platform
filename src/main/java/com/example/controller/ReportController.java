package com.example.controller;

import com.example.service.ComplaintService;
import com.example.service.ListingService;
import com.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

    private final UserService userService;
    private final ListingService listingService;
    private final ComplaintService complaintService;

    public ReportController(UserService userService, ListingService listingService, ComplaintService complaintService) {
        this.userService = userService;
        this.listingService = listingService;
        this.complaintService = complaintService;
    }

    @GetMapping
    public String reportDashboard(Model model) {

        long totalUsers = userService.findAll().size();
        long totalListings = listingService.findAll().size();
        long totalComplaints = complaintService.findAll().size();

        long pendingListings = listingService.findAll().stream().filter(l -> l.getStatus().equals("pending")).count();
        long pendingComplaints = complaintService.findPending().size();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalListings", totalListings);
        model.addAttribute("totalComplaints", totalComplaints);
        model.addAttribute("pendingListings", pendingListings);
        model.addAttribute("pendingComplaints", pendingComplaints);

        return "admin_report";
    }
}
