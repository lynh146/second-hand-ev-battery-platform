package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;  
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    
     
    @ManyToOne
    @JoinColumn(name = "SellerID")
    private User seller;

    
    @Column(name = "TotalAmount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

   
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
  
    @Column(name = "Status")
    private String status; 

    @Column(name = "ApprovedBy") 
    private Long approvedBy; 

     
    @Column(name = "ContractContent", columnDefinition = "TEXT")  
    private String contractContent;

    @Column(name = "DeliveryMethod")  
    private String deliveryMethod;
}