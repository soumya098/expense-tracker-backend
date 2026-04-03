package com.soumya.expense_tracker_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.expense_tracker_backend.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findByUserId(Long userId);

  List<Account> findByUserIdAndActiveTrue(Long userId);

  Optional<Account> findByIdAndUserId(Long accountId, Long userId);

  boolean existsByNameIgnoreCaseAndUserId(String name, Long userId);

  boolean existsByIdAndUserId(Long accountId, Long userId);
}
