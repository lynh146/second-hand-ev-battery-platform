package com.example.model;

<<<<<<< HEAD
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

=======
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@Entity
@Table(name = "PAYMENT")
=======
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentID;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "TransactionID", nullable = false)
    private Transaction transaction;
=======
    @JoinColumn(name = "UserID", nullable = false)
    private User user;          

    @ManyToOne
    @JoinColumn(name = "ListingID", nullable = false)
    private Listing listing;    
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c

    @Column(name = "Amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    @Column(name = "Status")
<<<<<<< HEAD
    private String status;
}
=======
    private String status;      

 
    @Column(name = "CommissionFee", precision = 10, scale = 2)
    private BigDecimal commissionFee;  
}
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
