package com.soumya.expense_tracker_backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
    @NotBlank String name,
    String type, // e.g. "CHECKING", "SAVINGS"
    @NotBlank String currency, // default "INR" in service if null
    BigDecimal currentBalance,
    String description,
    String accountNumber,
    String ifscCode) {
}