package com.engine.app.service;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engine.app.exception.InvalidReviewException;
import com.engine.app.model.Delivery;
import com.engine.app.model.Review;
import com.engine.app.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Delivery delivery, Integer score, Timestamp timestamp) throws InvalidReviewException {
        if (score < 1 || score > 5) {
            throw new InvalidReviewException("Invalid score");
        } else {
            Review review = new Review(delivery,score,timestamp);
            reviewRepository.save(review);
            return review;
        }
    }
    
}
