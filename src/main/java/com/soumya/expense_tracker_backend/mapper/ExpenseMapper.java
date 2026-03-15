package com.soumya.expense_tracker_backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.soumya.expense_tracker_backend.dto.ExpenseRequest;
import com.soumya.expense_tracker_backend.dto.ExpenseResponse;
import com.soumya.expense_tracker_backend.entity.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Expense toEntity(ExpenseRequest request);

  ExpenseResponse toResponse(Expense expense);

  List<ExpenseResponse> toResponseList(List<Expense> expenses);
}
