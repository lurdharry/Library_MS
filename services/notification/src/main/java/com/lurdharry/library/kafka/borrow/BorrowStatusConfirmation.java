package com.lurdharry.library.kafka.borrow;

import com.lurdharry.library.kafka.user.User;

import java.util.List;

public record BorrowStatusConfirmation(
        String bookOrderRef,
        User user,
        List<Book> books,
        ApprovalStatus status
) {
}
