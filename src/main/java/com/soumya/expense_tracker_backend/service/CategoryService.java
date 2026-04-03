package com.soumya.expense_tracker_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.dto.CategoryRequest;
import com.soumya.expense_tracker_backend.dto.CategoryResponse;
import com.soumya.expense_tracker_backend.entity.Category;
import com.soumya.expense_tracker_backend.entity.User;
import com.soumya.expense_tracker_backend.exception.ResourceExistsException;
import com.soumya.expense_tracker_backend.exception.ResourceNotFoundException;
import com.soumya.expense_tracker_backend.mapper.CategoryMapper;
import com.soumya.expense_tracker_backend.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper modelMapper;

  public List<CategoryResponse> getAll(User user) {
    List<Category> categories = categoryRepository.findAllForUser(user.getId());

    return modelMapper.toResponseList(categories);
  }

  public CategoryResponse create(CategoryRequest request, User user) {

    if (categoryRepository.existsForUserOrDefault(request.name().trim(), user.getId())) {
      throw new ResourceExistsException("Category already exists");
    }

    Category category = modelMapper.toEntity(request);
    category.setUser(user);
    category.setIsDefault(false);
    category = categoryRepository.save(category);
    return modelMapper.toResponse(category);
  }

  public void delete(Long id, User user) {
    Category category = categoryRepository.findByIdAndUserId(id, user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    boolean isDefault = category.getIsDefault();
    if (isDefault) {
      throw new IllegalArgumentException("Cannot delete system default categories");
    }

    // 🚫 Future-safe: check usage in transactions
    // if (transactionRepository.existsByCategoryId(categoryId)) {
    // throw new IllegalStateException("Category is used in transactions and cannot
    // be deleted");
    // }
    categoryRepository.delete(category);
  }
}
