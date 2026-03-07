package com.soumya.expense_tracker_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soumya.expense_tracker_backend.dto.ExpenseRequest;
import com.soumya.expense_tracker_backend.dto.ExpenseResponse;
import com.soumya.expense_tracker_backend.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {
  private final ExpenseService expenseService;

  @PostMapping
  public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
    ExpenseResponse response = expenseService.createExpense(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
    return ResponseEntity.ok(expenseService.getAllExpenses());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
    return ResponseEntity.ok(expenseService.getExpenseById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id,
      @Valid @RequestBody ExpenseRequest request) {
    return ResponseEntity.ok(expenseService.updateExpense(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
    expenseService.deleteExpense(id);
    return ResponseEntity.noContent().build();
  }
}
