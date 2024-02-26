package com.trustpilot.parser.service;

import com.trustpilot.parser.cache.DomainReviewCacheService;
import com.trustpilot.parser.model.DomainReview;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DomainReviewParseService {
  private final Logger logger = LogManager.getLogger();
  private final DomainReviewCacheService cacheRepository;
  private final PageParser pageParser;

  public DomainReviewParseService(DomainReviewCacheService cacheRepository, PageParser pageParser) {
    this.cacheRepository = cacheRepository;
    this.pageParser = pageParser;
  }

  public Flux<DomainReview> parseDomains(List<String> domainUrls) {
    return Flux.fromIterable(domainUrls)
      .flatMap(this::parseDomain);
  }

  public Mono<DomainReview> parseDomain(String domainUrl) {
    logger.info("[DomainReviewParseService] Parse URL: {}", domainUrl);
    return Mono.fromCallable(() -> domainUrl)
      .flatMap(pageParser::parsePage)
      .flatMap(review -> cacheRepository.save(domainUrl, review))
      .switchIfEmpty(Mono.defer(() -> {
        logger.info("[DomainReviewParseService] This domain has already been parsed");
        return Mono.empty();
      }));
  }
}
