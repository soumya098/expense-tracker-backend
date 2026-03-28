package com.soumya.expense_tracker_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.soumya.expense_tracker_backend.security.CustomUserDetailsService;
import com.soumya.expense_tracker_backend.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final PasswordEncoder passwordEncoder;
  private final JwtAuthFilter jwtFilter;
  private final CustomUserDetailsService userDetailsService;

  public SecurityConfig(JwtAuthFilter jwtFilter, CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.jwtFilter = jwtFilter;
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity hs) throws Exception {
    return hs
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated())
        .authenticationProvider(authenticationProvider(passwordEncoder))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }
}
