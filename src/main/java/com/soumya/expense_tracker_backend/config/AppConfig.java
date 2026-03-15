package com.soumya.expense_tracker_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12); // strength 12 is a good modern default
    // You can also use: new BCryptPasswordEncoder() → default strength 10
  }

  // ... other security beans can go here later (JWT, etc.)
}
