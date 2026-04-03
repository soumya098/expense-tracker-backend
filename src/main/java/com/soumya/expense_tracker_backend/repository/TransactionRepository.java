package com.soumya.expense_tracker_backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.expense_tracker_backend.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Optional<Transaction> findByIdAndUserId(Long id, Long userId);

  List<Transaction> findByUserId(Long userId);

  List<Transaction> findByUserIdAndAccountId(Long userId, Long accountId);

  List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

  List<Transaction> findByUserIdAndAccountIdAndDateBetween(Long userId, Long accountId, LocalDate startDate,
      LocalDate endDate);
}
