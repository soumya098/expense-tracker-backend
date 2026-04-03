package com.soumya.expense_tracker_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soumya.expense_tracker_backend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  boolean existsByNameIgnoreCaseAndIsDefaultTrue(String name);

  @Query("""
          SELECT COUNT(c) > 0 FROM Category c
          WHERE LOWER(c.name) = LOWER(:name)
          AND (c.isDefault = true OR (c.isDefault = false AND c.user.id = :userId))
      """)
  boolean existsForUserOrDefault(String name, Long userId);

  List<Category> findByIsDefaultTrue();

  Optional<Category> findByIdAndUserId(Long id, Long userId);

  @Query("SELECT c FROM Category c WHERE c.user.id = :userId OR c.isDefault = true")
  List<Category> findAllForUser(Long userId);
}