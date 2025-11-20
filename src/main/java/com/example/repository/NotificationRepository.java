// Module chung – dùng cho User & Admin
package com.example.repository;

import com.example.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Lấy danh sách notification của 1 user, mới nhất trước
    List<Notification> findByUser_UserIDOrderByCreatedAtDesc(Long userId);

    // Đếm số thông báo chưa đọc – dùng để hiển thị badge
    long countByUser_UserIDAndIsReadFalse(Long userId);
}
