package com.soumya.expense_tracker_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.expense_tracker_backend.entity.RefreshToken;
import com.soumya.expense_tracker_backend.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  void deleteByUser(User user);
}