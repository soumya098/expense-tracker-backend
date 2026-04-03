package com.soumya.expense_tracker_backend.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class NormalizationService {

  @Named("normalize")
  public String normalize(String value) {
    if (value == null)
      return null;
    return value.trim().toLowerCase();
  }

  @Named("trim")
  public String trim(String value) {
    if (value == null)
      return null;
    return value.trim();
  }
}
