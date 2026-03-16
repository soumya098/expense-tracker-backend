package com.soumya.expense_tracker_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.expense_tracker_backend.constant.TransactionType;
import com.soumya.expense_tracker_backend.entity.Category;
import com.soumya.expense_tracker_backend.entity.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByTypeAndIsDefaultTrue(TransactionType type);

  List<Category> findByUserAndType(User user, TransactionType type);

}