package com.soumya.expense_tracker_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.expense_tracker_backend.dto.AuthResponse;
import com.soumya.expense_tracker_backend.dto.LoginRequest;
import com.soumya.expense_tracker_backend.dto.RefreshTokenRequest;
import com.soumya.expense_tracker_backend.dto.UserRegistrationRequest;
import com.soumya.expense_tracker_backend.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public AuthResponse register(@RequestBody UserRegistrationRequest request) {
    return authService.register(request);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody LoginRequest request) {
    return authService.login(request);
  }

  @PostMapping("/refresh")
  public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
    return authService.refreshToken(request);
  }
}