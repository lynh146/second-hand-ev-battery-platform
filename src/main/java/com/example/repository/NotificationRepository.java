// Module chung – dùng cho User & Admin
package com.example.repository;

import com.example.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser_UserIDOrderByCreatedAtDesc(Long userId);

    long countByUser_UserIDAndIsReadFalse(Long userId);
}
