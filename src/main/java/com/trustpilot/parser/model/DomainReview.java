package com.trustpilot.parser.model;

public class DomainReview {
  private String name;
  private Long reviewCounts;
  private Double rating;

  public DomainReview() {
  }

  public DomainReview(String name, Long reviewCounts, Double rating) {
    this.name = name;
    this.reviewCounts = reviewCounts;
    this.rating = rating;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getReviewCounts() {
    return reviewCounts;
  }

  public void setReviewCounts(Long reviewCounts) {
    this.reviewCounts = reviewCounts;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  @Override
  public String toString() {
    return "DomainReview{" +
      "name='" + name + '\'' +
      ", reviewCounts=" + reviewCounts +
      ", rating=" + rating +
      '}';
  }
}
