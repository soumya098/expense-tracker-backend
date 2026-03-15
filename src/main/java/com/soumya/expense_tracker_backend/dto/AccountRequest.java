package com.soumya.expense_tracker_backend.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
    @NotBlank String name,
    String type, // e.g. "CHECKING", "SAVINGS"
    @NotBlank String currency, // default "INR" in service if null
    String description,
    String accountNumber,
    String ifscCode) {
}