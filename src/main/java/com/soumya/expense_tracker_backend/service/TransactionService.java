package com.soumya.expense_tracker_backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.constant.TransactionType;
import com.soumya.expense_tracker_backend.dto.TransactionRequest;
import com.soumya.expense_tracker_backend.dto.TransactionResponse;
import com.soumya.expense_tracker_backend.entity.Account;
import com.soumya.expense_tracker_backend.entity.Category;
import com.soumya.expense_tracker_backend.entity.Transaction;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.exception.ResourceNotFoundException;
import com.soumya.expense_tracker_backend.mapper.TransactionMapper;
import com.soumya.expense_tracker_backend.repository.AccountRepository;
import com.soumya.expense_tracker_backend.repository.CategoryRepository;
import com.soumya.expense_tracker_backend.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final AccountRepository accountRepo;
  private final CategoryRepository categoryRepo;
  private final TransactionRepository transactionRepo;
  private final TransactionMapper transactionMapper;

  @Transactional
  public TransactionResponse createTransaction(User user, TransactionRequest request) {
    validateRequest(request);

    // 1. Validate account ownership
    Account account = accountRepo.findByIdAndUserId(request.accountId(), user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

    BigDecimal amount = request.amount();
    TransactionType type = request.type();

    Transaction transaction = new Transaction();
    transaction.setUser(user);
    transaction.setAccount(account);
    transaction.setAmount(amount);
    transaction.setType(request.type());
    transaction.setNotes(request.note());
    transaction.setDate(LocalDate.now());

    switch (type) {
      case INCOME -> {
        Category category = validateCategory(request.categoryId(), user, TransactionType.INCOME);
        account.setCurrentBalance(account.getCurrentBalance().add(amount));
        transaction.setCategory(category);
      }
      case EXPENSE -> {
        Category category = validateCategory(request.categoryId(), user, TransactionType.EXPENSE);
        if (account.getCurrentBalance().compareTo(amount) < 0) {
          throw new IllegalArgumentException("Insufficient balance");
        }
        account.setCurrentBalance(account.getCurrentBalance().subtract(amount));
        transaction.setCategory(category);
      }
      case TRANSFER -> {
        if (request.toAccountId() == null) {
          throw new IllegalArgumentException("To account is required for transfer");
        }

        if (request.accountId().equals(request.toAccountId())) {
          throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        Account toAccount = accountRepo.findByIdAndUserId(request.toAccountId(), user.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Target account not found"));

        if (account.getCurrentBalance().compareTo(amount) < 0) {
          throw new IllegalArgumentException("Insufficient balance for transfer");
        }
        account.setCurrentBalance(account.getCurrentBalance().subtract(amount));
        toAccount.setCurrentBalance(toAccount.getCurrentBalance().add(amount));
        transaction.setToAccount(toAccount);
        transaction.setDescription("Transfer to " + toAccount.getName());
      }
    }

    Transaction savedTransaction = transactionRepo.save(transaction);
    return transactionMapper.toResponse(savedTransaction);
  }

  public List<TransactionResponse> getTransactionsForUser(User user) {
    return transactionRepo.findByUserId(user.getId()).stream()
        .map(transactionMapper::toResponse)
        .toList();
  }

  public TransactionResponse getById(User user, Long id) {
    Transaction txn = transactionRepo.findByIdAndUserId(id, user.getId())
        .orElseThrow(() -> new RuntimeException("Transaction not found"));

    return transactionMapper.toResponse(txn);
  }

  public List<TransactionResponse> getTransactionsForAccount(User user, Long accountId) {
    return transactionRepo.findByUserIdAndAccountId(user.getId(), accountId).stream()
        .map(transactionMapper::toResponse)
        .toList();
  }

  // update and delete methods can be implemented similarly with appropriate
  // validations

  private void validateRequest(TransactionRequest req) {
    if (req.type() == null) {
      throw new IllegalArgumentException("Transaction type is required");
    }

    if (req.type() != TransactionType.TRANSFER && req.categoryId() == null) {
      throw new IllegalArgumentException("Category is required");
    }
  }

  private Category validateCategory(Long categoryId, User user, TransactionType type) {
    Category category = categoryRepo.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    // Ownership check
    boolean isDefault = category.getIsDefault();
    if (!isDefault && !category.getUser().getId().equals(user.getId())) {
      throw new RuntimeException("Unauthorized access to category");
    }

    // Type check
    if (category.getType() != type) {
      throw new IllegalArgumentException("Category type does not match transaction type");
    }

    return category;
  }
}
