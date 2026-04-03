package com.soumya.expense_tracker_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.soumya.expense_tracker_backend.constant.TransactionType;

public record TransactionResponse(
    Long id,
    BigDecimal amount,
    TransactionType type,
    String note,
    LocalDate date,

    Long accountId,
    String accountName,

    Long toAccountId,
    String toAccountName,

    Long categoryId,
    String categoryName,
    boolean systemCategory) {
}