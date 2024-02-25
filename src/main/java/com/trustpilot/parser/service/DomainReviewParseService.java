package com.trustpilot.parser.service;

import static com.trustpilot.parser.model.ParseStatus.FINISH;
import static com.trustpilot.parser.model.ParseStatus.PROCESSING;

import com.trustpilot.parser.model.Domain;
import com.trustpilot.parser.model.DomainReview;
import com.trustpilot.parser.repository.DomainRepository;
import com.trustpilot.parser.repository.DomainReviewRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DomainReviewParseService {
  private final Logger logger = LogManager.getLogger();
  private final DomainRepository domainRepository;
  private final DomainReviewRepository domainReviewRepository;
  private final PageParser pageParser;

  public DomainReviewParseService(
    DomainRepository domainRepository,
    DomainReviewRepository domainReviewRepository,
    PageParser pageParser
  ) {
    this.domainRepository = domainRepository;
    this.domainReviewRepository = domainReviewRepository;
    this.pageParser = pageParser;
  }

  public Flux<DomainReview> parseDomains(List<String> domainUrls) {
    return Flux.fromIterable(domainUrls)
      .flatMap(this::parseDomain);
  }

  public Mono<DomainReview> parseDomain(String domainUrl) {
    logger.info("[DomainReviewParseService] Parse URL: {}", domainUrl);
    return Mono.fromCallable(() -> domainUrl)
      .filterWhen(this::existsNotByDomainUrl)
      .flatMap(pageParser::parsePage)
      .flatMap(review -> saveDomainReview(review, domainUrl))
      .switchIfEmpty(Mono.defer(() -> {
        logger.info("[DomainReviewParseService] This domain has already been parsed");
        return Mono.empty();
      }));
  }

  private Mono<Boolean> existsNotByDomainUrl(String domainUrl) {
    return domainRepository.countByDomainUrl(domainUrl)
      .map(count -> count == 0);
  }

  private Mono<DomainReview> saveDomainReview(DomainReview domainReview, String url) {
    return saveDomain(url)
      .map(domainId -> {
          domainReview.setDomainId(domainId);
          return domainReview;
        }
      )
      .flatMap(domainReviewRepository::save)
      .delayUntil(review -> domainRepository.updateParseStatusById(review.getDomainId(), FINISH));
  }

  private Mono<Long> saveDomain(String url) {
    return domainRepository.save(new Domain(url, PROCESSING))
      .map(Domain::getId);
  }
}
