package com.soumya.expense_tracker_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soumya.expense_tracker_backend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Boolean existsByNameAndIsDefaultTrue(String name);

  List<Category> findByIsDefaultTrue();

  List<Category> findByIdAndUserId(Long id, Long userId);

  @Query("SELECT c FROM Category c WHERE c.user.id = :userId OR c.isDefault = true")
  List<Category> findAllForUser(Long userId);

}