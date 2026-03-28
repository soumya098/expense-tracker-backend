package com.soumya.expense_tracker_backend.dto;

import com.soumya.expense_tracker_backend.constant.TransactionType;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    @NotBlank String name,
    TransactionType type) {

}
