package com.soumya.expense_tracker_backend.dto;

import com.soumya.expense_tracker_backend.constant.TransactionType;

public record CategoryRequest(
    String name,
    TransactionType type) {

}
