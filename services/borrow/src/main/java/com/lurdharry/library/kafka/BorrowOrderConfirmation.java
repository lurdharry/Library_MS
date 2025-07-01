package com.lurdharry.library.kafka;


import com.lurdharry.library.book.BookResponse;
import com.lurdharry.library.user.UserResponse;

import java.util.List;

public record BorrowOrderConfirmation(
        String bookOrderRef,
        UserResponse user,
        List<BookResponse> books
) {
}
