package com.soumya.expense_tracker_backend.dto;

import java.time.LocalDateTime;

import com.soumya.expense_tracker_backend.constant.TransactionType;

public record CategoryResponse(
    Long id,
    String name,
    TransactionType type,
    Boolean isDefault,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
