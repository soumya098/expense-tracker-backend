package com.soumya.expense_tracker_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soumya.expense_tracker_backend.dto.UserRegistrationRequest;
import com.soumya.expense_tracker_backend.dto.UserResponse;
import com.soumya.expense_tracker_backend.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * Register a new user
   */
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserResponse> registerUser(
      @Valid @RequestBody UserRegistrationRequest request) {

    UserResponse response = userService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Get current authenticated user's profile
   * (will be secured later with JWT)
   */
  @GetMapping("/me")
  public ResponseEntity<UserResponse> getCurrentUser() {
    // Temporary implementation – replace with SecurityContextHolder in next step
    // For now: you can pass userId as param or use a test user
    // In real scenario: User currentUser = userService.getCurrentUser();
    throw new UnsupportedOperationException("Implement with authentication");
    // UserResponse response = userService.getCurrentUserProfile();
    // return ResponseEntity.ok(response);
  }

  /**
   * Get user by ID (admin or self only – secured later)
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
    UserResponse response = userService.getUserById(id);
    return ResponseEntity.ok(response);
  }

  /**
   * Update own profile (fullName, etc.)
   * Password change should be separate endpoint
   */
  // @PatchMapping("/me")
  // public ResponseEntity<UserResponse> updateProfile(
  // @Valid @RequestBody UserUpdateRequest request) {

  // // Temporary – replace with current user
  // throw new UnsupportedOperationException("Implement with authentication");

  // // User updated = userService.updateProfile(currentUserId, request);
  // // return ResponseEntity.ok(modelMapper.map(updated, UserResponse.class));
  // }

  /**
   * Delete own account (soft-delete or hard-delete depending on policy)
   */
  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOwnAccount() {
    // Temporary
    throw new UnsupportedOperationException("Implement with authentication");

    // userService.deleteUser(currentUserId);
  }

  // ────────────────────────────────────────────────
  // Future / optional admin endpoints (protected)
  // ────────────────────────────────────────────────

  // @GetMapping
  // @PreAuthorize("hasRole('ADMIN')")
  // public ResponseEntity<List<UserResponse>> getAllUsers(
  // @RequestParam(defaultValue = "0") int page,
  // @RequestParam(defaultValue = "20") int size) {
  // // pagination implementation later
  // }

  // @DeleteMapping("/{id}")
  // @PreAuthorize("hasRole('ADMIN')")
  // public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
  // userService.deleteUser(id);
  // return ResponseEntity.noContent().build();
  // }
}
