package com.soumya.expense_tracker_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soumya.expense_tracker_backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsernameIgnoreCase(String username);

  boolean existsByEmailIgnoreCase(String email);

  Optional<User> findByUsernameIgnoreCase(String username);

  Optional<User> findByEmailIgnoreCase(String email);

  @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username) OR LOWER(u.email) = LOWER(:email)")
  Optional<User> findByUsernameOrEmail(String username, String email);
}
