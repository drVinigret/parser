package com.trustpilot.parser.service;

import com.trustpilot.parser.model.Domain;
import com.trustpilot.parser.model.DomainNotFoundException;
import com.trustpilot.parser.model.DomainReview;
import com.trustpilot.parser.model.DomainReviewDTO;
import com.trustpilot.parser.repository.DomainRepository;
import com.trustpilot.parser.repository.DomainReviewRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

public class DomainReviewService {
  private final Logger logger = LogManager.getLogger();
  private DomainRepository domainRepository;
  private DomainReviewRepository domainReviewRepository;
  private DomainReviewParseService domainReviewParseService;

  @Value("${parse.domain.url.prefix}")
  private String domainUrlPrefix;

  public DomainReviewService(DomainRepository domainRepository, DomainReviewRepository domainReviewRepository, DomainReviewParseService domainReviewParseService) {
    this.domainRepository = domainRepository;
    this.domainReviewRepository = domainReviewRepository;
    this.domainReviewParseService = domainReviewParseService;
  }

  public Mono<DomainReviewDTO> getReview(String domainName) {
    return processDomain(domainName)
      .flatMap(domainReviewRepository::getDomainReviewByDomainId)
      .map(this::getDomainReviewDTO)
      .switchIfEmpty(Mono.defer(() -> {
        logger.info("[DomainReviewService] This domain will be processed: {}", domainName);
        return Mono.error(new DomainNotFoundException("Domain not found"));
      }));
  }

  private Mono<Long> processDomain(String domainName) {
    return domainRepository.findParsedDomainByDomainName(domainName)
      .map(Domain::getId)
      .switchIfEmpty(Mono.defer(() -> {
        logger.info("[DomainReviewService] This domain will be processed: {}", domainName);
        return parseDomainReviewByDomainName(domainName);
      }));
  }

  private DomainReviewDTO getDomainReviewDTO(DomainReview domainReview) {
    return new DomainReviewDTO(domainReview.getReviewCounts(), domainReview.getRating());
  }

  private Mono<Long> parseDomainReviewByDomainName(String domainName) {
    String domainUrl = domainUrlPrefix + domainName;
    return domainReviewParseService.parseDomain(domainUrl)
      .map(DomainReview::getDomainId);
  }
}
