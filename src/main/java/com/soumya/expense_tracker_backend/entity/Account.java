package com.soumya.expense_tracker_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts", uniqueConstraints={ @UniqueConstraint(columnNames={"user_id", "name"}) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String name;

  @Column(length = 50)
  private String type; // CHECKING, SAVINGS, CREDIT_CARD, CASH, INVESTMENT

  @Column(nullable = false)
  private String currency; // "INR", "USD" — default "INR"

  @Builder.Default
  private Boolean active = true;

  private BigDecimal currentBalance; // optional – can be computed instead

  private String description;

  private String accountNumber;

  private String ifscCode;

  @OneToMany(mappedBy = "account")
  private List<Transaction> transactions;

  @Column(updatable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
