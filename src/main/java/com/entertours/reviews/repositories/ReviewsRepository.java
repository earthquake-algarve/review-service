package com.entertours.reviews.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entertours.reviews.models.ReviewsModel;

public interface ReviewsRepository extends JpaRepository<ReviewsModel, UUID> {
    
    boolean existsByComment(String comment);
    boolean existsByRating(String rating);
}
