package com.soumya.expense_tracker_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.dto.AccountRequest;
import com.soumya.expense_tracker_backend.dto.AccountResponse;
import com.soumya.expense_tracker_backend.entity.Account;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.exception.ResourceNotFoundException;
import com.soumya.expense_tracker_backend.mapper.AccountMapper;
import com.soumya.expense_tracker_backend.repository.AccountRepository;
import com.soumya.expense_tracker_backend.util.NormalizationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final AccountMapper modelMapper;
  private final NormalizationService normalizationService;

  public AccountResponse createAccount(AccountRequest request, User user) {
    if (accountRepository.existsByNameIgnoreCaseAndUserId(normalizationService.trim(request.name()), user.getId())) {
      throw new IllegalArgumentException("Account already exists");
    }
    Account account = modelMapper.toEntity(request);
    account.setUser(user);

    Account saved = accountRepository.save(account);

    return modelMapper.toResponse(saved);
  }

  public List<AccountResponse> getAllAccountsForUser(User user) {
    return modelMapper.toResponseList(accountRepository.findByUserId(user.getId()));
  }

  public List<AccountResponse> getAllActiveAccountsForUser(User user) {
    return modelMapper.toResponseList(accountRepository.findByUserIdAndActiveTrue(user.getId()));
  }

  public AccountResponse getAccount(Long accountId, User user) {
    Account account = accountRepository.findByIdAndUserId(accountId, user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

    return modelMapper.toResponse(account);
  }

  public AccountResponse updateAccount(Long accountId, User user, AccountRequest request) {
    Account account = accountRepository.findByIdAndUserId(accountId, user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

    String normalizedName = normalizationService.trim(request.name());
    account.setName(normalizedName != null ? normalizedName : account.getName());
    account.setType(request.type() != null ? request.type() : account.getType());
    account.setCurrency(request.currency() != null ? request.currency() : account.getCurrency());
    account
        .setCurrentBalance(request.currentBalance() != null ? request.currentBalance() : account.getCurrentBalance());
    account.setDescription(request.description() != null ? request.description() : account.getDescription());
    account.setAccountNumber(request.accountNumber() != null ? request.accountNumber() : account.getAccountNumber());
    account.setIfscCode(request.ifscCode() != null ? request.ifscCode() : account.getIfscCode());

    Account saved = accountRepository.save(account);

    return modelMapper.toResponse(saved);
  }

  public void deleteAccount(Long accountId, User user) {
    Account account = accountRepository.findByIdAndUserId(accountId, user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    account.setActive(false); // soft delete
    accountRepository.save(account);
  }
}
