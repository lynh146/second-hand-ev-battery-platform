package com.example.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Admin;
import com.example.model.Listing;
import com.example.model.Notification;
import com.example.repository.AdminRepository;
import com.example.repository.NotificationRepository;
import com.example.service.IListingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/listings")
public class AdminListingController {

    private final IListingService listingService;
    private final NotificationRepository notificationRepository;
    private final AdminRepository adminRepository;

    // hien thi danh sach tin dang cho duyet
    @GetMapping("/pending")
    public String viewPendingListings(Model model) {
        model.addAttribute("pendingListings", listingService.getPendingListings());

        model.addAttribute("activePage", "listings-pending");
        model.addAttribute("pageTitle", "Tin chờ duyệt");

        return "admin_listing_approve";
    }

    // admin duyệt tin 
    @PostMapping("/{id}/approve")
    public String approveListing(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {

        Admin admin = adminRepository.findById(1L).orElse(null);

        Listing listing = listingService.approveListing(id, admin, true);

        if (listing.getUser() != null) {
            Notification noti = Notification.builder()
                    .user(listing.getUser())
                    .title("Tin đăng đã được duyệt")
                    .message("Tin '" + listing.getTitle() + "' đã được admin duyệt và hiển thị PUBLIC.")
                    .type("LISTING_APPROVED")
                    .createdAt(LocalDateTime.now())
                    .isRead(false)
                    .build();
            notificationRepository.save(noti);
        }

        redirectAttributes.addFlashAttribute("message", "Đã duyệt tin ID " + id);
        return "redirect:/admin/listings/pending";
    }

    // admin từ chối tin
    @PostMapping("/{id}/reject")
    public String rejectListing(@PathVariable Long id,
                                @RequestParam(name = "reason", required = false) String reason,
                                RedirectAttributes redirectAttributes) {

        Admin admin = adminRepository.findById(1L).orElse(null);

        Listing listing = listingService.approveListing(id, admin, false);

        if (listing.getUser() != null) {
            String msg = "Tin '" + listing.getTitle() + "' đã bị từ chối.";
            if (reason != null && !reason.isBlank()) {
                msg += " Lý do: " + reason;
            }

            Notification noti = Notification.builder()
                    .user(listing.getUser())
                    .title("Tin đăng bị từ chối")
                    .message(msg)
                    .type("LISTING_REJECTED")
                    .createdAt(LocalDateTime.now())
                    .isRead(false)
                    .build();
            notificationRepository.save(noti);
        }

        redirectAttributes.addFlashAttribute("message", "Đã từ chối tin ID " + id);
        return "redirect:/admin/listings/pending";
    }
}
