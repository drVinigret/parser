package com.trustpilot.parser.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.trustpilot.parser.model.DomainReview;
import reactor.core.publisher.Mono;

public class DomainReviewCacheService {
  private static final Cache<String, DomainReview> cache = Caffeine.newBuilder().build();

  public Mono<DomainReview> get(String domainName) {
    return Mono.fromCallable(() -> domainName)
      .mapNotNull(cache::getIfPresent);
  }

  public Mono<DomainReview> save(String domainUrl, DomainReview domainReview) {
    return Mono.fromCallable(() -> {
      cache.put(domainUrl, domainReview);
      return domainReview;
    });
  }
}
