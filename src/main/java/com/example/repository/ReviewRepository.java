package com.example.repository;

import com.example.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTransactionListingListingID(Long listingID);

    Page<Review> findByTransactionListingListingID(Long listingID, Pageable pageable);
}
