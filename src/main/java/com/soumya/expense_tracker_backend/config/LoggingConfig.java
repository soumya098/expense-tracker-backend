package com.soumya.expense_tracker_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingConfig {

  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();

    filter.setIncludeQueryString(true); // ?param=value...
    filter.setIncludePayload(true); // request body (JSON, form data, etc.)
    filter.setMaxPayloadLength(10000); // limit body size to avoid flooding logs (adjust as needed)
    filter.setIncludeHeaders(true); // Authorization, Content-Type, etc.
    filter.setIncludeClientInfo(true); // remote IP, session ID (if any)

    // Optional: prefix for easier spotting
    filter.setBeforeMessagePrefix("REQUEST IN  → ");
    filter.setAfterMessagePrefix("REQUEST OUT → ");

    return filter;
  }
}