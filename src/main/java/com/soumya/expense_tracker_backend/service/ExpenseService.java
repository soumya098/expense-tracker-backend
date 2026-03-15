package com.soumya.expense_tracker_backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.dto.ExpenseRequest;
import com.soumya.expense_tracker_backend.dto.ExpenseResponse;
import com.soumya.expense_tracker_backend.entity.Expense;
import com.soumya.expense_tracker_backend.exception.ResourceNotFoundException;
import com.soumya.expense_tracker_backend.mapper.ExpenseMapper;
import com.soumya.expense_tracker_backend.repository.ExpenseRepository;
import com.soumya.expense_tracker_backend.specification.ExpenseSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
  private final ExpenseRepository expenseRepository;
  private final ExpenseMapper modelMapper;

  public ExpenseResponse createExpense(ExpenseRequest expenseRequest) {
    Expense expense = Expense.builder()
        .amount(expenseRequest.getAmount())
        .category(expenseRequest.getCategory())
        .date(expenseRequest.getDate())
        .description(expenseRequest.getDescription())
        .build();

    Expense savedExpense = expenseRepository.save(expense);
    return mapToResponse(savedExpense);
  }

  public List<ExpenseResponse> getAllExpenses() {
    return modelMapper.toResponseList(expenseRepository.findAll());
  }

  public List<ExpenseResponse> getAllExpenses(
      String category,
      LocalDate startDate,
      LocalDate endDate,
      BigDecimal minAmount,
      BigDecimal maxAmount) {

    Specification<Expense> spec = ExpenseSpecification.withFilters(
        category, startDate, endDate, minAmount, maxAmount);

    return modelMapper.toResponseList(expenseRepository.findAll(spec));
  }

  public ExpenseResponse getExpenseById(Long id) {
    Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    return mapToResponse(expense);
  }

  public ExpenseResponse updateExpense(Long id, ExpenseRequest expenseRequest) {
    Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

    expense.setAmount(expenseRequest.getAmount());
    expense.setCategory(expenseRequest.getCategory());
    expense.setDate(expenseRequest.getDate());
    expense.setDescription(expenseRequest.getDescription());

    Expense updatedExpense = expenseRepository.save(expense);
    return modelMapper.toResponse(updatedExpense);
  }

  public void deleteExpense(Long id) {
    if (!expenseRepository.existsById(id)) {
      throw new ResourceNotFoundException("Expense not found with id: " + id);
    }
    expenseRepository.deleteById(id);
  }

  private ExpenseResponse mapToResponse(Expense expense) {
    return ExpenseResponse.builder()
        .id(expense.getId())
        .amount(expense.getAmount())
        .category(expense.getCategory())
        .date(expense.getDate())
        .description(expense.getDescription())
        .createdAt(expense.getCreatedAt())
        .updatedAt(expense.getUpdatedAt())
        .build();
  }
}
