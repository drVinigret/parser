package com.trustpilot.parser.model;

public class DomainReviewDTO {
  private Long reviewsCount;
  private Double rating;

  public DomainReviewDTO(Long reviewsCount, Double rating) {
    this.reviewsCount = reviewsCount;
    this.rating = rating;
  }

  public Long getReviewsCount() {
    return reviewsCount;
  }

  public void setReviewsCount(Long reviewsCount) {
    this.reviewsCount = reviewsCount;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }
}
