package com.soumya.expense_tracker_backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.soumya.expense_tracker_backend.dto.TransactionResponse;
import com.soumya.expense_tracker_backend.entity.Transaction;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface TransactionMapper {

  @Mapping(target = "accountId", source = "account.id")
  @Mapping(target = "accountName", source = "account.name")
  @Mapping(target = "categoryId", expression="java(transaction.getCategory() != null ? transaction.getCategory().getId() : null)")
  @Mapping(target = "categoryName", expression = "java(transaction.getCategory() != null ? transaction.getCategory().getName() : null)")
  @Mapping(target = "systemCategory", expression = "java(transaction.getCategory() != null && transaction.getCategory().isSystemCategory())")
  @Mapping(target = "toAccountId", expression = "java(transaction.getToAccount() != null ? transaction.getToAccount().getId() : null)")
  @Mapping(target = "toAccountName", expression = "java(transaction.getToAccount() != null ? transaction.getToAccount().getName() : null)")
  @Mapping(target = "note", source = "notes")
  @Mapping(target = "isCredit", expression = "java(isCredit(transaction, accountId))")
  TransactionResponse toResponse(Transaction transaction);

  List<TransactionResponse> toResponseList(List<Transaction> transactions);

  default boolean isCredit(Transaction txn, Long accountId) {
    return txn.getToAccount() != null && txn.getToAccount().getId().equals(accountId);
  }
}
