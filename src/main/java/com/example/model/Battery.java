<<<<<<< HEAD
package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BATTERY")
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BatteryID")
    private Long batteryID;

    @Column(name = "SerialNumber", length = 100)
    private String serialNumber;

    @Column(name = "Capacity")
    private Integer capacity; // mAh or kWh depending on domain

    @Column(name = "Status")
    private String status;

}
=======
package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BATTERY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BatteryID")
    private Long batteryID;

    @ManyToOne
    @JoinColumn(name = "OwnerID", nullable = false)
    private User owner;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Capacity_kWh", precision = 10, scale = 2)
    private BigDecimal capacityKWh;

    @Column(name = "BatteryCondition")
    private String batteryCondition; 

    @Column(name = "Description")
    private String description;

    @Column(name = "Price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "Status")
    private String status;
}
>>>>>>> d664fcabc26651422b2166690fe524b9fd43f076
