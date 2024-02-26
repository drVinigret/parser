package com.trustpilot.parser.config;

import com.trustpilot.parser.controller.DevController;
import com.trustpilot.parser.controller.DomainReviewsController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {
  private final ServiceConfig serviceConfig;

  public ControllerConfig(ServiceConfig serviceConfig) {
    this.serviceConfig = serviceConfig;
  }

  @Bean
  public DevController devController() {
    return new DevController(serviceConfig.domainReviewParseService());
  }

  @Bean
  public DomainReviewsController domainReviewsController() {
    return new DomainReviewsController(serviceConfig.domainReviewService());
  }
}
