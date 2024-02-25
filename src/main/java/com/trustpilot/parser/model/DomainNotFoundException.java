package com.trustpilot.parser.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DomainNotFoundException extends RuntimeException {
  public DomainNotFoundException(String message) {
    super(message);
  }
}
