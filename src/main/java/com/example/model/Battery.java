package com.example.model;

<<<<<<< HEAD
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

=======
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BATTERY")
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@Entity
@Table(name = "BATTERY")
=======
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BatteryID")
    private Long batteryID;

<<<<<<< HEAD
    @Column(name = "Brand")
    private String brand;

    @Column(name = "Capacity")
    private int capacity; // đơn vị: kWh

    @Column(name = "Voltage")
    private double voltage;

    @Column(name = "Condition")
    private String condition; // Mới, cũ, đã qua sử dụng

    @Column(name = "Status")
    private String status;
}
=======
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
>>>>>>> 602ebb081002c20119ae4fa1c52352337486f36c
