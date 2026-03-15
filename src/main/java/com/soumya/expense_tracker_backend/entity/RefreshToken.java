package com.soumya.expense_tracker_backend.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class RefreshToken {

  @Id
  @GeneratedValue
  private Long id;

  private String token;

  private Instant expiryDate;

  @ManyToOne
  private User user;
}