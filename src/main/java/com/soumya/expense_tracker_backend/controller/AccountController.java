package com.soumya.expense_tracker_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.expense_tracker_backend.dto.AccountRequest;
import com.soumya.expense_tracker_backend.dto.AccountResponse;
import com.soumya.expense_tracker_backend.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  // Temporary – later secured + current user from token
  @PostMapping
  public ResponseEntity<AccountResponse> createAccount(
      @RequestParam Long userId, // temporary
      @Valid @RequestBody AccountRequest request) {

    AccountResponse account = accountService.createAccount(userId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(account);
  }

  @GetMapping
  public ResponseEntity<List<AccountResponse>> getMyAccounts(
      @RequestParam Long userId) { // temporary

    List<AccountResponse> accounts = accountService.getAccountsForUser(userId);

    return ResponseEntity.ok(accounts);
  }
}