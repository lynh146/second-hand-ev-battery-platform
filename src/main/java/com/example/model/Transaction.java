package com.example.model;
<<<<<<< HEAD

import java.math.BigDecimal;
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

=======
 
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "TRANSACTION")
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private Long transactionID;

    @ManyToOne
    @JoinColumn(name = "ListingID")
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "BuyerID")
    private User buyer;

    @Column(name = "TotalAmount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "TransactionDate")
    private LocalDateTime transactionDate;

    @Column(name = "Status")
    private String status;
}
=======
public class Transaction {
 
    @Id
    @GeneratedValue(strategy =
GenerationType.IDENTITY)
    @Column(name =
"TransactionID")
    private Long transactionID;
 
    @ManyToOne
    @JoinColumn(name =
"BuyerID", nullable = false)
    private User buyer;
 
    @ManyToOne
    @JoinColumn(name =
"SellerID", nullable = false)  
    private User seller;
 
    @ManyToOne
    @JoinColumn(name =
"ListingID", nullable = false)
    private Listing listing;
 
    @OneToOne
    @JoinColumn(name =
"PaymentID")  
    private Payment payment;
 
    @Column(name =
"ContractContent")  
    private String contractContent;
 
    @Column(name =
"DeliveryMethod")  
    private String deliveryMethod;
 
    @Column(name =
"CreatedAt") 
    private LocalDateTime createdAt;
 
    @Column(name =
"Status") 
    private String status;
   
 
    @ManyToOne
    @JoinColumn(name =
"ApprovedBy")  
    private Admin approvedBy;
 
    @Column(name =
"TotalAmount", precision = 10, scale = 2)  
    private BigDecimal totalAmount;
}
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
