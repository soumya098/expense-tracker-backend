package com.soumya.expense_tracker_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
  private Long id;
  private BigDecimal amount;
  private String category;
  private LocalDate date;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}