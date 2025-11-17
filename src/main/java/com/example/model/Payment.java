package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentID;

    // GIỮ NGUYÊN: Khóa ngoại TransactionID (Đúng theo sơ đồ ERD)
    @ManyToOne
    @JoinColumn(name = "TransactionID", nullable = false)
    private Transaction transaction;

    @Column(name = "Amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    @Column(name = "Status")
    private String status;


    @Column(name = "CommissionFee", precision = 10, scale = 2)  
    private BigDecimal commissionFee;

   
    @Column(name = "PaymentGatewayRef")  
    private String paymentGatewayRef;

    @Column(name = "GatewayTransactionID")  
    private String gatewayTransactionID;

    @Column(name = "GatewayResponseCode")  
    private String gatewayResponseCode;
    
    @Column(name = "SecureHash")  
}