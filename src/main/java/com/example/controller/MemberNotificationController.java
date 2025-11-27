package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.model.Notification;
import com.example.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/notifications")
public class MemberNotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping
    public String listNotifications(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof CustomUserDetails principal)) {
            return "redirect:/login";
        }

        Long userId = principal.getUser().getUserID();

        List<Notification> notifications =
                notificationRepository.findByUser_UserIDOrderByCreatedAtDesc(userId);

        long unreadCount = notificationRepository.countByUser_UserIDAndIsReadFalse(userId);

        model.addAttribute("notifications", notifications);
        model.addAttribute("unreadCount", unreadCount);

        return "notification";
    }

    @PostMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof CustomUserDetails principal)) {
            return "redirect:/login";
        }

        Long currentUserId = principal.getUser().getUserID();

        notificationRepository.findById(id).ifPresent(n -> {
            if (n.getUser() != null && currentUserId.equals(n.getUser().getUserID())) {
                n.setIsRead(true);
                notificationRepository.save(n);
            }
        });

        return "redirect:/member/notifications";
    }
}

