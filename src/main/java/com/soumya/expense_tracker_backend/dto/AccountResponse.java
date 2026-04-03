package com.soumya.expense_tracker_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.soumya.expense_tracker_backend.constant.AccountType;

public record AccountResponse(
    Long id,
    String name,
    AccountType type,
    boolean isDefault,
    String currency,
    BigDecimal currentBalance,
    String description,
    String accountNumber,
    String ifscCode,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
