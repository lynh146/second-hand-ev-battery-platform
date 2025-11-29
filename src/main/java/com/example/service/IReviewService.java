package com.example.service;

import com.example.model.Review;
import com.example.model.Transaction;
import com.example.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface IReviewService {

    boolean canReview(Long transactionId, Long reviewerId);

    void writeReview(Long transactionId, Long reviewerId, int rating, String comment);

    List<Review> getReviewsOfUser(Long userId);

    List<Review> getReviewsWritten(Long reviewerId);

    List<Transaction> getPendingReviews(Long userId);

    Page<Review> getReviewsByListing(Long listingID, Pageable pageable);
}
