package com.soumya.expense_tracker_backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soumya.expense_tracker_backend.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Optional<Transaction> findByIdAndUserId(Long id, Long userId);

  List<Transaction> findByUserId(Long userId);

  List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

  List<Transaction> findByUserIdAndAccountIdAndDateBetween(Long userId, Long accountId, LocalDate startDate,
      LocalDate endDate);

  boolean existsByAccountIdOrToAccountId(Long accountId, Long toAccountId);

  @Query("""
          SELECT t FROM Transaction t
          JOIN FETCH t.account
          LEFT JOIN FETCH t.toAccount
          LEFT JOIN FETCH t.category
          WHERE t.user.id = :userId
          AND (t.account.id = :accountId OR t.toAccount.id = :accountId)
          ORDER BY t.date DESC
      """)
  List<Transaction> findByUserIdAndAccountId(Long userId, Long accountId);
}
