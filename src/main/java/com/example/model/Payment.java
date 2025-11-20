<<<<<<< HEAD
package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;           

    @ManyToOne
    @JoinColumn(name = "ListingID", nullable = false)
    private Listing listing;     

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
}
=======
package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;           

    @ManyToOne
    @JoinColumn(name = "ListingID", nullable = false)
    private Listing listing;     

    @Column(name = "Amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    @Column(name = "Status")
    private String status;   .

    @Column(name = "CommissionFee", precision = 10, scale = 2)
    private BigDecimal commissionFee;  
}
>>>>>>> d664fcabc26651422b2166690fe524b9fd43f076
