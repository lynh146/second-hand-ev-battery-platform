package com.example.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "NOTIFICATION") 
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID") 
    private Long notificationID;

    @ManyToOne
    @JoinColumn(name = "UserID")  
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
