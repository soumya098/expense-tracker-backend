package com.soumya.expense_tracker_backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.soumya.expense_tracker_backend.dto.CategoryRequest;
import com.soumya.expense_tracker_backend.dto.CategoryResponse;
import com.soumya.expense_tracker_backend.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true) // Set manually in service
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "transactions", ignore = true)
  @Mapping(target = "isDefault", ignore = true)
  Category toEntity(CategoryRequest request);

  CategoryResponse toResponse(Category category);

  List<CategoryResponse> toResponseList(List<Category> category);
}
