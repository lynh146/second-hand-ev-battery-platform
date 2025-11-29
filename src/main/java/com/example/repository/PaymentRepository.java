package com.example.repository;

import com.example.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUser_UserID(Long userId);

    List<Payment> findByListing_ListingID(Long listingId);

    List<Payment> findByStatus(String status);
}
