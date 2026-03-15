package com.soumya.expense_tracker_backend.dto;

import java.time.LocalDateTime;

public record UserResponse(
                Long id,
                String username,
                String email,
                String fullName,
                LocalDateTime createdAt) {
}
