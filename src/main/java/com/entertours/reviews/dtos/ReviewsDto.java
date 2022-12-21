package com.entertours.reviews.dtos;

import jakarta.validation.constraints.NotBlank;

public class ReviewsDto {
    @NotBlank
    private String comment;
    
    @NotBlank
    private String rating;
    

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
