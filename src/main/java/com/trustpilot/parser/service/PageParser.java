package com.trustpilot.parser.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trustpilot.parser.model.DomainReview;
import java.io.IOException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import reactor.core.publisher.Mono;

public class PageParser {
  private final Logger logger = LogManager.getLogger();

  public Mono<DomainReview> parsePage(String url) {
    return Mono.fromCallable(() -> url)
      .mapNotNull(this::getDocumentFromPage)
      .map(this::getBusinessUnit)
      .map(this::getDomainReview)
      .doOnNext(domainReview -> logger.info("[PageParser] Data after parsing: {}", domainReview))
      .switchIfEmpty(Mono.defer(() -> {
        logger.warn("[PageParser] Script element with ID '__NEXT_DATA__' not found.");
        return Mono.empty();
      }));
  }

  private Document getDocumentFromPage(String url) {
    try {
      return Jsoup.connect(url).get();
    } catch (IOException e) {
      logger.error("[PageParser] Error parsing page", e);
    }
    return null;
  }

  private JsonObject getBusinessUnit(Document document) {
    return Optional.ofNullable(document.select("script#__NEXT_DATA__").first().firstChild())
      .map(node -> {
        String scriptData = String.valueOf(node);
        return JsonParser.parseString(scriptData).getAsJsonObject();
      })
      .map(jsonObject -> jsonObject.getAsJsonObject("props"))
      .map(propsObject -> propsObject.getAsJsonObject("pageProps"))
      .map(pagePropsObject -> pagePropsObject.getAsJsonObject("businessUnit"))
      .orElse(null);
  }

  private DomainReview getDomainReview(JsonObject businessUnit) {
    String name = businessUnit.get("displayName").getAsString();
    Long numberOfReviews = businessUnit.get("numberOfReviews").getAsLong();
    Double trustScore = businessUnit.get("trustScore").getAsDouble();
    return new DomainReview(name, numberOfReviews, trustScore);
  }
}
