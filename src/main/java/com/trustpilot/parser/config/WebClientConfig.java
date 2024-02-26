package com.trustpilot.parser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
  @Value("${maxInMemorySize}")
  private int maxInMemorySize;

  @Bean
  public WebClient webClient() {
    ExchangeStrategies strategies = ExchangeStrategies.builder()
      .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(maxInMemorySize))
      .build();
    return WebClient.builder().exchangeStrategies(strategies).build();
  }
}
