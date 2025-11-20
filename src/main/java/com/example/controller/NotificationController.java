package com.example.controller;

import com.example.model.Notification;
import com.example.model.User;
import com.example.service.NotificationService;
import com.example.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService,
                                  UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getAll(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(notificationService.getAll(user));
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnread(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(notificationService.getUnread(user));
    }

    @PostMapping("/send")
    public ResponseEntity<Notification> send(
            @RequestParam Long userId,
            @RequestParam String message) {

        User user = userService.findById(userId);
        return ResponseEntity.ok(notificationService.send(user, message));
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }
}
