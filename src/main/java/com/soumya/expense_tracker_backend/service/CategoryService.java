package com.soumya.expense_tracker_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soumya.expense_tracker_backend.dto.CategoryRequest;
import com.soumya.expense_tracker_backend.dto.CategoryResponse;
import com.soumya.expense_tracker_backend.entity.Category;
import com.soumya.expense_tracker_backend.entity.User;
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
    Category category = modelMapper.toEntity(request);
    category.setUser(user);
    category.setIsDefault(false);
    category = categoryRepository.save(category);
    return modelMapper.toResponse(category);
  }
}
