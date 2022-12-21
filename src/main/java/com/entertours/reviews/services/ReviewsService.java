package com.entertours.reviews.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.entertours.reviews.models.ReviewsModel;
import com.entertours.reviews.repositories.ReviewsRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewsService {

    final ReviewsRepository reviewsRepository;

    public ReviewsService(ReviewsRepository reviewsRepository){
        this.reviewsRepository = reviewsRepository;
    }

    @Transactional
    public ReviewsModel save(ReviewsModel reviewsModel){

        return reviewsRepository.save(reviewsModel);
    }

    public boolean existsComment(String comment){
        return reviewsRepository.existsByComment(comment);
    }

    public boolean existsRating(String rating){
        return reviewsRepository.existsByRating(rating);
    }

    public List<ReviewsModel> findAll() {
        return  reviewsRepository.findAll();
    }

    public Optional<ReviewsModel> findById(UUID id) {
        return reviewsRepository.findById(id);
    }

    @Transactional
    public void delete(ReviewsModel reviewsModel){
        reviewsRepository.delete(reviewsModel);
    }
}
