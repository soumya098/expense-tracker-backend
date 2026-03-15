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
import com.soumya.expense_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final UserRepository userRepository; // later: replace with security

  private final AccountRepository accountRepository;
  private final AccountMapper modelMapper;
  private final UserService userService;

  public AccountResponse createAccount(Long userId, AccountRequest request) {
    User user = userService.findByUserId(userId); // or getCurrentUser()
    // later: replace with authenticated user

    Account account = Account.builder()
        .user(user)
        .name(request.name())
        .type(request.type())
        .currency(request.currency() != null ? request.currency() : "INR")
        .description(request.description())
        .build();

    Account saved = accountRepository.save(account);

    return modelMapper.toResponse(saved);
  }

  public List<AccountResponse> getAccountsForUser(Long userId) {
    if (!userRepository.existsById(userId)) { // later: replace with security
      throw new ResourceNotFoundException("User not found with id: " + userId);
    }
    return modelMapper.toResponseList(accountRepository.findByUserId(userId));
  }

  public AccountResponse getAccount(Long accountId, Long userId) {
    Account account = accountRepository.findByIdAndUserId(accountId, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

    return modelMapper.toResponse(account);
  }
}
