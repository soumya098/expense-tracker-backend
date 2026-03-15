package com.soumya.expense_tracker_backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.entity.RefreshToken;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository repo;

  public RefreshToken createRefreshToken(User user) {

    RefreshToken token = new RefreshToken();

    token.setUser(user);
    token.setToken(UUID.randomUUID().toString());
    token.setExpiryDate(
        Instant.now().plus(7, ChronoUnit.DAYS));

    return repo.save(token);
  }

  public RefreshToken verifyExpiration(RefreshToken token) {

    if (token.getExpiryDate().isBefore(Instant.now())) {
      repo.delete(token);
      throw new RuntimeException("Refresh token expired");
    }

    return token;
  }

  public RefreshToken findByToken(String token) {
    return repo.findByToken(token).orElseThrow(() -> new RuntimeException("Refresh token not found"));
  }
}