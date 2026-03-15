package com.soumya.expense_tracker_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
    Long id,
    String name,
    String type,
    String currency,
    BigDecimal currentBalance,
    String description,
    String accountNumber,
    String ifscCode,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
