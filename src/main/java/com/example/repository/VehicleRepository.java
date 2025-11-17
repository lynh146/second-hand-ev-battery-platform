package com.example.repository;

import com.example.model.Vehicle;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByBrandContainingIgnoreCase(String brand);

    List<Vehicle> findByModelContainingIgnoreCase(String model);

    List<Vehicle> findByManufactureYear(int manufactureYear);

    List<Vehicle> findByStatus(String status);

    List<Vehicle> findByOwner(User owner);
}