package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COMPLAINT")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComplaintID")
    private Long complaintID;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "Status", nullable = false)
    private String status; // pending / resolved / rejected

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "ProcessedAt")
    private LocalDateTime processedAt;

    @ManyToOne
    @JoinColumn(name = "TransactionID", nullable = false)
    private Transaction transaction;

    
    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;
}
