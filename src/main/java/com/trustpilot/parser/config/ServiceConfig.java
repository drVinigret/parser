package com.trustpilot.parser.config;

import com.trustpilot.parser.cache.DomainReviewCacheService;
import com.trustpilot.parser.service.DomainReviewParseService;
import com.trustpilot.parser.service.DomainReviewService;
import com.trustpilot.parser.service.PageParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
  private final WebClientConfig webClientConfig;

  public ServiceConfig(WebClientConfig webClientConfig) {
    this.webClientConfig = webClientConfig;
  }

  @Bean
  public DomainReviewCacheService domainReviewCacheService() {
    return new DomainReviewCacheService();
  }

  @Bean
  public DomainReviewParseService domainReviewParseService() {
    return new DomainReviewParseService(domainReviewCacheService(), parser());
  }

  @Bean
  public DomainReviewService domainReviewService() {
    return new DomainReviewService(domainReviewCacheService(), domainReviewParseService());
  }

  @Bean
  public PageParser parser() {
    return new PageParser(webClientConfig.webClient());
  }
}
