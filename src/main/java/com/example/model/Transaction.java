package com.example.model;
 
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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