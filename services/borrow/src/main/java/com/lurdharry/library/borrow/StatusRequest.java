package com.lurdharry.library.borrow;

public record StatusRequest(
        ApprovalStatus status,
        String borrowId
) {
}
