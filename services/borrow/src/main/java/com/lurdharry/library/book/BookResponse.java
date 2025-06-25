package com.lurdharry.library.book;

import lombok.Builder;

@Builder
public record BookResponse(
        String bookId
) {
}
