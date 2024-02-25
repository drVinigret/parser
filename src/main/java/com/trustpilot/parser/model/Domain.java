package com.trustpilot.parser.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("domain")
public class Domain {
  @Id
  private Long id;
  private String domainUrl;
  private ParseStatus parseStatus;

  public Domain() {
  }

  public Domain(String domainUrl, ParseStatus parseStatus) {
    this.domainUrl = domainUrl;
    this.parseStatus = parseStatus;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDomainUrl() {
    return domainUrl;
  }

  public void setDomainUrl(String domainUrl) {
    this.domainUrl = domainUrl;
  }

  public ParseStatus getParseStatus() {
    return parseStatus;
  }

  public void setParseStatus(ParseStatus parseStatus) {
    this.parseStatus = parseStatus;
  }

  @Override
  public String toString() {
    return "Domain{" +
      "id=" + id +
      ", domainUrl='" + domainUrl + '\'' +
      ", parseStatus=" + parseStatus +
      '}';
  }
}
