package com.example.service;

import com.example.model.Review;
import com.example.model.Transaction;
import com.example.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReviewService {

    void writeReview(User user, Transaction transaction, String comment, int rating);

    List<Review> getReviewsByListing(Long listingID);

    Page<Review> getReviewsByListing(Long listingID, Pageable pageable);
}
