package com.soumya.expense_tracker_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soumya.expense_tracker_backend.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findByUserId(Long userId);

  List<Account> findByUserIdAndActiveTrue(Long userId);

  Optional<Account> findByIdAndUserId(Long accountId, Long userId);

  boolean existsByNameIgnoreCaseAndUserId(String name, Long userId);

  @Query("""
          SELECT COUNT(a) > 0 FROM Account a
          WHERE LOWER(a.name) = LOWER(:name)
          AND a.user.id = :userId
          AND (:excludeId IS NULL OR a.id <> :excludeId)
      """)
  boolean existsByNameIgnoreCaseAndUserIdExcludingId(
      String name,
      Long userId,
      Long excludeId);

  boolean existsByIdAndUserId(Long accountId, Long userId);

  boolean existsByUserIdAndIsDefaultTrue(Long userId);

  Optional<Account> findByUserIdAndIsDefaultTrue(Long userId);
}
