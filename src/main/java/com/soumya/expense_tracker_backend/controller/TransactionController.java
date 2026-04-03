package com.soumya.expense_tracker_backend.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.expense_tracker_backend.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.soumya.expense_tracker_backend.dto.TransactionRequest;
import com.soumya.expense_tracker_backend.dto.TransactionResponse;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.security.UserPrincipal;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping
  public ResponseEntity<TransactionResponse> create(@RequestBody @Valid TransactionRequest request,
      @AuthenticationPrincipal UserPrincipal principal) {

    return ResponseEntity.ok(transactionService.createTransaction(principal.getUser(), request));
  }

  @GetMapping
  public ResponseEntity<List<TransactionResponse>> getAll(
      @AuthenticationPrincipal(expression = "user") User user) {
    return ResponseEntity.ok(transactionService.getTransactionsForUser(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionResponse> getById(@AuthenticationPrincipal(expression = "user") User user,
      @PathVariable Long id) {
    return ResponseEntity.ok(transactionService.getById(user, id));
  }
}
