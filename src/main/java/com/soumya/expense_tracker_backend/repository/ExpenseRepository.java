package com.soumya.expense_tracker_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.soumya.expense_tracker_backend.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
  // Useful query methods (add more later)

  List<Expense> findByCategory(String category);

  List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

  // Later: sum by category, month, etc. → we'll use @Query
}
