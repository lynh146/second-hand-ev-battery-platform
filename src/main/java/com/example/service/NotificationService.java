package com.example.service;

import com.example.model.Notification;
import com.example.model.User;

import java.util.List;

public interface NotificationService {
    Notification send(User user, String message);
    List<Notification> getAll(User user);
    List<Notification> getUnread(User user);
    Notification markAsRead(Long id);
}
