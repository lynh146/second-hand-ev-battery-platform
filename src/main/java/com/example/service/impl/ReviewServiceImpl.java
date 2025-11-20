package com.example.service.impl;

import com.example.model.Review;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.ReviewRepository;
import com.example.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements IReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public void writeReview(User user, Transaction transaction, String comment, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction must not be null");
        }

        if (comment == null) comment = "";

        Review review = new Review();
        review.setReviewer(user);              
        review.setTransaction(transaction);    
        review.setComment(comment);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getReviewsByListing(Long listingID) {
        return reviewRepository.findByTransactionListingListingID(listingID);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getReviewsByListing(Long listingID, Pageable pageable) {
        return reviewRepository.findByTransactionListingListingID(listingID, pageable);
    }
}
