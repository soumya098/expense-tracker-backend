package com.soumya.expense_tracker_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.dto.AuthResponse;
import com.soumya.expense_tracker_backend.dto.LoginRequest;
import com.soumya.expense_tracker_backend.dto.RefreshTokenRequest;
import com.soumya.expense_tracker_backend.dto.UserRegistrationRequest;
import com.soumya.expense_tracker_backend.entity.RefreshToken;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.repository.UserRepository;
import com.soumya.expense_tracker_backend.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;

  public AuthResponse register(UserRegistrationRequest request) {

    User user = new User();
    user.setUsername(request.username());
    user.setEmail(request.email());
    user.setFullName(request.fullName());
    user.setPasswordHash(passwordEncoder.encode(request.password()));

    userRepository.save(user);

    String accessToken = jwtService.generateToken(user);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

    return new AuthResponse(refreshToken.getToken(), accessToken, user.getUsername());
  }

  public AuthResponse login(LoginRequest request) {

    User user = userRepository.findByUsername(request.username())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = jwtService.generateToken(user);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

    return new AuthResponse(refreshToken.getToken(), token, user.getUsername());
  }

  public AuthResponse refreshToken(RefreshTokenRequest request) {

    RefreshToken token = refreshTokenService.findByToken(request.refreshToken());

    refreshTokenService.verifyExpiration(token);

    User user = token.getUser();

    String accessToken = jwtService.generateToken(user);

    return new AuthResponse(
        accessToken,
        request.refreshToken(),
        user.getUsername());
  }
}
