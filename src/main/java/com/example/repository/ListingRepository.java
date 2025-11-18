package com.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.model.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Lấy danh sách tin theo status (pending, approved, rejected)
    List<Listing> findByStatus(String status);
}
