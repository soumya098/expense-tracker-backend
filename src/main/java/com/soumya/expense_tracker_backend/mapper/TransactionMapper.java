package com.soumya.expense_tracker_backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.soumya.expense_tracker_backend.dto.TransactionResponse;
import com.soumya.expense_tracker_backend.entity.Transaction;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy=NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface TransactionMapper {

  @Mapping(target = "accountId", source = "account.id")
  @Mapping(target = "accountName", source = "account.name")
  @Mapping(target = "categoryId", source = "category.id")
  @Mapping(target = "categoryName", source = "category.name")
  @Mapping(target = "systemCategory", source = "category.isDefault")
  @Mapping(target = "toAccountId", source = "toAccount.id")
  @Mapping(target = "toAccountName", source = "toAccount.name")
  @Mapping(target = "note", source = "notes")
  TransactionResponse toResponse(Transaction transaction);

  List<TransactionResponse> toResponseList(List<Transaction> transactions);
}
