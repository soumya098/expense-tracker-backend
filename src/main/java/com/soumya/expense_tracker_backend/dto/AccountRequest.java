package com.soumya.expense_tracker_backend.dto;

import java.math.BigDecimal;

import com.soumya.expense_tracker_backend.constant.AccountType;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
    @NotBlank String name,
    AccountType type,
    @NotBlank String currency,
    BigDecimal currentBalance,
    String description,
    String accountNumber,
    String ifscCode) {
}