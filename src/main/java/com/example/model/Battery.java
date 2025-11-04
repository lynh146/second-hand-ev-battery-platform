package com.example.model;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BATTERY")
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BatteryID")
    private Long batteryID;

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
