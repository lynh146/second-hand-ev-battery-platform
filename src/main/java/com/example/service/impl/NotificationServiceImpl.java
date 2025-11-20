package com.example.service.impl;

import com.example.model.Notification;
import com.example.model.User;
import com.example.repository.NotificationRepository;
import com.example.service.NotificationService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification send(User user, String message) {
        Notification noti = Notification.builder()
                .user(user)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        return notificationRepository.save(noti);
    }

    @Override
    public List<Notification> getAll(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<Notification> getUnread(User user) {
        return notificationRepository.findByUserAndIsReadFalse(user);
    }

    @Override
    public Notification markAsRead(Long id) {
        Notification noti = notificationRepository.findById(id).orElse(null);
        if (noti == null) return null;

        noti.setRead(true);
        return notificationRepository.save(noti);
    }
}
