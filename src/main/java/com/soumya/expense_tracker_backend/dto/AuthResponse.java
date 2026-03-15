package com.soumya.expense_tracker_backend.dto;

public record AuthResponse(
                String refreshToken,
                String accessToken,
                String username) {

}
