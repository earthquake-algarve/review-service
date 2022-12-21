package com.entertours.reviews.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entertours.reviews.dtos.ReviewsDto;
import com.entertours.reviews.models.ReviewsModel;
import com.entertours.reviews.services.ReviewsService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/reviews")
public class ReviewsController {

    final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewsModel>> getAllReviews(){
        return ResponseEntity.status(HttpStatus.OK).body(reviewsService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneReview(@PathVariable(value= "id") UUID id){
        Optional <ReviewsModel> reviewsModelOptional = reviewsService.findById(id);

        if(!reviewsModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(reviewsModelOptional.get());
    }


    @PostMapping
    public ResponseEntity<Object> saveReview(@RequestBody @Valid ReviewsDto reviewsDto){

        var reviewsModel = new ReviewsModel();
        BeanUtils.copyProperties(reviewsDto, reviewsModel);
        reviewsModel.setReviewDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewsService.save(reviewsModel));
        
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value= "id") UUID id, @RequestBody @Valid ReviewsDto reviewsDto){
        
        Optional <ReviewsModel> reviewsModelOptional = reviewsService.findById(id);
        
        if(!reviewsModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }

        var reviewModel = reviewsModelOptional.get();
        reviewModel.setComment(reviewsDto.getComment());
        reviewModel.setRating(reviewsDto.getRating());


        /* //OR
        var reviewModel= new ReviewsModel();
        BeanUtils.copyProperties(reviewsDto, reviewsModel);
        reviewsModel.setId(reviewsModelOptional.get().getId());
        reviewsModel.setReviewDate(reviewsModelOptional.get().getReviewDate()); */

        return ResponseEntity.status(HttpStatus.OK).body(reviewsService.save(reviewModel));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value= "id") UUID id){
        Optional <ReviewsModel> reviewsModelOptional = reviewsService.findById(id);
        
        if(!reviewsModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }

        reviewsService.delete(reviewsModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Review deleted successfully!");
    }
}
