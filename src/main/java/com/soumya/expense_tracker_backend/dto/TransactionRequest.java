package com.soumya.expense_tracker_backend.dto;

import java.math.BigDecimal;

import com.soumya.expense_tracker_backend.constant.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
        @NotNull @DecimalMin(value = "0.01", message = "Amount must be greater than 0") BigDecimal amount,
        TransactionType type,
        @NotNull Long accountId,
        Long categoryId,
        Long toAccountId,
        String note) {
}