package com.soumya.expense_tracker_backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.soumya.expense_tracker_backend.dto.AccountRequest;
import com.soumya.expense_tracker_backend.dto.AccountResponse;
import com.soumya.expense_tracker_backend.entity.Account;
import com.soumya.expense_tracker_backend.util.NormalizationService;

@Mapper(componentModel = "spring", uses = NormalizationService.class)
public interface AccountMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true) // Set manually in service
  @Mapping(target = "currentBalance", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "transactions", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "name", source = "name", qualifiedByName = "trim")
  Account toEntity(AccountRequest request);

  AccountResponse toResponse(Account account);

  List<AccountResponse> toResponseList(List<Account> accounts);
}