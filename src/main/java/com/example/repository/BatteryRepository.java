package com.example.repository;

import com.example.model.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByBrandContainingIgnoreCase(String brand);
    List<Battery> findByStatus(String status);
}
