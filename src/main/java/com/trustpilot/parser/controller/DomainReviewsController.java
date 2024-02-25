package com.trustpilot.parser.controller;

import com.trustpilot.parser.model.DomainReviewDTO;
import com.trustpilot.parser.service.DomainReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DomainReviewsController {
  private final DomainReviewService domainReviewService;

  public DomainReviewsController(DomainReviewService domainReviewService) {
    this.domainReviewService = domainReviewService;
  }

  @GetMapping("/reviews/{domain}")
  public Mono<DomainReviewDTO> getReviewData(@PathVariable("domain") String domain) {
    return domainReviewService.getReview(domain);
  }
}
