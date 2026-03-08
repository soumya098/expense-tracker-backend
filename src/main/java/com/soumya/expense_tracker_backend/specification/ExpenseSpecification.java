package com.soumya.expense_tracker_backend.specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.soumya.expense_tracker_backend.entity.Expense;

import jakarta.persistence.criteria.Predicate;

public class ExpenseSpecification {
  public static Specification<Expense> withFilters(
      String category,
      LocalDate startDate,
      LocalDate endDate,
      BigDecimal minAmount,
      BigDecimal maxAmount) {

    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Category filter (case-insensitive contains / like)
      if (category != null && !category.trim().isEmpty()) {
        predicates.add(cb.like(
            cb.lower(root.get("category")),
            "%" + category.trim().toLowerCase() + "%"));
      }

      // Date range
      if (startDate != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("expenseDate"), startDate));
      }
      if (endDate != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("expenseDate"), endDate));
      }

      // Amount range
      if (minAmount != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), minAmount));
      }
      if (maxAmount != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("amount"), maxAmount));
      }

      // Combine all conditions with AND
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
