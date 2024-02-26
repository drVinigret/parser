package com.trustpilot.parser.service;

import com.trustpilot.parser.cache.DomainReviewCacheService;
import com.trustpilot.parser.model.DomainNotFoundException;
import com.trustpilot.parser.model.DomainReview;
import com.trustpilot.parser.model.DomainReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

public class DomainReviewService {
  private final Logger logger = LogManager.getLogger();
  private final DomainReviewCacheService cacheRepository;
  private final DomainReviewParseService domainReviewParseService;

  @Value("${parse.domain.url.prefix}")
  private String domainUrlPrefix;

  public DomainReviewService(DomainReviewCacheService cacheRepository, DomainReviewParseService domainReviewParseService) {
    this.cacheRepository = cacheRepository;
    this.domainReviewParseService = domainReviewParseService;
  }

  public Mono<DomainReviewDTO> getReview(String domainName) {
    return processDomain(domainName)
      .map(this::getDomainReviewDTO)
      .switchIfEmpty(Mono.defer(() -> {
        logger.info("[DomainReviewService] This domain not found: {}", domainName);
        return Mono.error(new DomainNotFoundException("Domain not found"));
      }));
  }

  private Mono<DomainReview> processDomain(String domainName) {
    String domainUrl = domainUrlPrefix + domainName;
    return cacheRepository.get(domainUrl)
      .doOnNext(review -> logger.info("[DomainReviewService] Get review from cache"))
      .switchIfEmpty(Mono.defer(() -> {
        logger.info("[DomainReviewService] This domain will be processed: {}", domainName);
        return domainReviewParseService.parseDomain(domainUrl);
      }));
  }

  private DomainReviewDTO getDomainReviewDTO(DomainReview domainReview) {
    return new DomainReviewDTO(domainReview.getReviewCounts(), domainReview.getRating());
  }
}
