package com.trustpilot.parser.controller;

import com.trustpilot.parser.model.DomainReview;
import com.trustpilot.parser.model.DomainUrl;
import com.trustpilot.parser.service.DomainReviewParseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DevController {
  private final DomainReviewParseService domainReviewParseService;

  public DevController(DomainReviewParseService domainReviewParseService) {
    this.domainReviewParseService = domainReviewParseService;
  }

  @GetMapping("/parse")
  public Flux<DomainReview> parseDomains(@RequestBody DomainUrl domainUrl) {
    return domainReviewParseService.parseDomains(domainUrl.getUrls());
  }
}
