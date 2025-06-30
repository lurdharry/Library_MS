package com.lurdharry.library.kafka.borrow;


import java.util.List;

public record BorrowOrderConfirmation(
        String bookOrderRef,
        User   user,
        List<Book> books
) {
}
