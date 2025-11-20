<<<<<<< HEAD
package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleID;

    private String brand;
    private String model;
    private int year;
}
=======
package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "VEHICLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VehicleID")
    private Long vehicleID;

    @ManyToOne
    @JoinColumn(name = "OwnerID", nullable = false)
    private User owner;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Model")
    private String model;

    @Column(name = "Type")
    private String type;

    @Column(name = "ManufactureYear")
    private int manufactureYear;

    @Column(name = "Description")
    private String description;

    @Column(name = "Capacity")
    private String capacity;   

    @Column(name = "Mileage")
    private int mileage;

    @Column(name = "Status")
    private String status;   

    @Column(name = "Price", precision = 10, scale = 2)
    private BigDecimal price;
}
>>>>>>> d664fcabc26651422b2166690fe524b9fd43f076
