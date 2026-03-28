package com.soumya.expense_tracker_backend.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.expense_tracker_backend.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.soumya.expense_tracker_backend.dto.CategoryRequest;
import com.soumya.expense_tracker_backend.dto.CategoryResponse;
import com.soumya.expense_tracker_backend.security.UserPrincipal;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAllCategories(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    return ResponseEntity.ok(categoryService.getAll(userPrincipal.getUser()));
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestBody CategoryRequest request) {
    return ResponseEntity.ok(categoryService.create(request, userPrincipal.getUser()));
  }

}
