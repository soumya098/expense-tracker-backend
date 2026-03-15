package com.soumya.expense_tracker_backend.dto;

public record LoginRequest(
    String username,
    String password) {
}