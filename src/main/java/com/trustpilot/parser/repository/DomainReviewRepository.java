package com.trustpilot.parser.repository;

import com.trustpilot.parser.model.DomainReview;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DomainReviewRepository extends ReactiveCrudRepository<DomainReview, Long> {
  Mono<DomainReview> getDomainReviewByDomainId(Long domainId);
}
