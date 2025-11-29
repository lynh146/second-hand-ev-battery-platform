package com.example.service.impl;

import com.example.model.Review;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.ReviewRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;

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

    private final ReviewRepository reviewRepo;
    private final TransactionRepository txRepo;
    private final UserRepository userRepo;

    @Override
    public boolean canReview(Long txId, Long reviewerId) {
        return !reviewRepo.existsByTransaction_TransactionIDAndReviewer_UserID(txId, reviewerId);
    }

    @Override
    public void writeReview(Long txId, Long reviewerId, int rating, String comment) {

        Transaction tx = txRepo.findById(txId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        User reviewer = userRepo.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User target = tx.getBuyer().getUserID().equals(reviewerId)
                ? tx.getSeller()
                : tx.getBuyer();

        Review review = Review.builder()
                .transaction(tx)
                .reviewer(reviewer)
                .target(target)
                .rating(rating)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepo.save(review);

        tx.setReview(review);
        txRepo.save(tx);
    }

    @Override
    public List<Review> getReviewsOfUser(Long userId) {
        return reviewRepo.findByTarget_UserID(userId);
    }

    @Override
    public List<Review> getReviewsWritten(Long reviewerId) {
        return reviewRepo.findByReviewer_UserID(reviewerId);
    }

    @Override
    public List<Transaction> getPendingReviews(Long userId) {
        return txRepo.findByBuyer_UserIDAndReviewIsNull(userId);
    }

    @Override
    public Page<Review> getReviewsByListing(Long listingID, Pageable pageable) {
        return reviewRepo.findByTransactionListingListingID(listingID, pageable);
    }
}
