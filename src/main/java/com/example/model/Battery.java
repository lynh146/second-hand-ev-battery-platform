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
