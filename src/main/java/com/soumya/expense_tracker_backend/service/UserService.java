package com.soumya.expense_tracker_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.dto.UserRegistrationRequest;
import com.soumya.expense_tracker_backend.dto.UserResponse;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.exception.ResourceNotFoundException;
import com.soumya.expense_tracker_backend.mapper.UserMapper;
import com.soumya.expense_tracker_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper modelMapper;
  private final PasswordEncoder passwordEncoder; // we'll add BCrypt soon

  public UserResponse register(UserRegistrationRequest request) {
    if (userRepository.existsByUsername(request.username())) {
      throw new IllegalArgumentException("Username already taken");
    }
    if (userRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("Email already registered");
    }

    User user = modelMapper.toEntity(request);
    user.setPasswordHash(passwordEncoder.encode(request.password()));

    User saved = userRepository.save(user);

    return modelMapper.toResponse(saved);
  }

  // Optional: method to verify password later (for login)
  public boolean checkPassword(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  public User findByUserId(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
  }

  public UserResponse getUserById(Long id) {
    User user = findByUserId(id);
    return modelMapper.toResponse(user);
  }

  // Later: getCurrentUser() via SecurityContext
}
