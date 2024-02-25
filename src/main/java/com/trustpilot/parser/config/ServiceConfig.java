package com.trustpilot.parser.config;

import com.trustpilot.parser.repository.DomainRepository;
import com.trustpilot.parser.repository.DomainReviewRepository;
import com.trustpilot.parser.service.DomainReviewParseService;
import com.trustpilot.parser.service.DomainReviewService;
import com.trustpilot.parser.service.PageParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.trustpilot.parser")
public class ServiceConfig {
  private final DomainRepository domainRepository;
  private final DomainReviewRepository domainReviewRepository;

  public ServiceConfig(DomainRepository domainRepository, DomainReviewRepository domainReviewRepository) {
    this.domainRepository = domainRepository;
    this.domainReviewRepository = domainReviewRepository;
  }

  @Bean
  public DomainReviewParseService domainReviewParseService() {
    return new DomainReviewParseService(domainRepository, domainReviewRepository, parser());
  }

  @Bean
  public DomainReviewService domainReviewService() {
    return new DomainReviewService(domainRepository, domainReviewRepository, domainReviewParseService());
  }

  @Bean
  public PageParser parser() {
    return new PageParser();
  }
}
