package com.soumya.expense_tracker_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.expense_tracker_backend.dto.AccountRequest;
import com.soumya.expense_tracker_backend.dto.AccountResponse;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.security.UserPrincipal;
import com.soumya.expense_tracker_backend.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {

    AccountResponse account = accountService.createAccount(request, userPrincipal.getUser());
    return ResponseEntity.status(HttpStatus.CREATED).body(account);
  }

  @GetMapping
  public ResponseEntity<List<AccountResponse>> getAll(@AuthenticationPrincipal UserPrincipal userPrincipal) {

    List<AccountResponse> accounts = accountService.getAllAccountsForUser(userPrincipal.getUser());

    return ResponseEntity.ok(accounts);
  }

  @GetMapping("/active")
  public ResponseEntity<List<AccountResponse>> getAllActive(@AuthenticationPrincipal UserPrincipal userPrincipal) {

    List<AccountResponse> accounts = accountService.getAllActiveAccountsForUser(userPrincipal.getUser());
    return ResponseEntity.ok(accounts);
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long accountId,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {

    AccountResponse account = accountService.getAccount(accountId, userPrincipal.getUser());
    return ResponseEntity.ok(account);
  }

  @PutMapping("/{accountId}")
  public ResponseEntity<AccountResponse> update(@PathVariable Long accountId,
      @Valid @RequestBody AccountRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {

    AccountResponse account = accountService.updateAccount(accountId, userPrincipal.getUser(), request);
    return ResponseEntity.ok(account);
  }

  @DeleteMapping("/{accountId}")
  public ResponseEntity<Void> delete(@PathVariable Long accountId,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {

    accountService.deleteAccount(accountId, userPrincipal.getUser());
    return ResponseEntity.noContent().build();
  }
}