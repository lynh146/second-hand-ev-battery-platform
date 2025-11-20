package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private Long notificationID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "Title")
    private String title;

    @Column(name = "Message")
    private String message;

    @Column(name = "Type")
    private String type;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "IsRead")
    private Boolean isRead;
}
