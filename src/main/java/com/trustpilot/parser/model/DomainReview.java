package com.trustpilot.parser.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("domain_review")
public class DomainReview {
  @Id
  private Long id;
  private Long domainId;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDomainId() {
    return domainId;
  }

  public void setDomainId(Long domainId) {
    this.domainId = domainId;
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
      "id=" + id +
      ", domainId=" + domainId +
      ", name='" + name + '\'' +
      ", reviewCounts=" + reviewCounts +
      ", rating=" + rating +
      '}';
  }
}
