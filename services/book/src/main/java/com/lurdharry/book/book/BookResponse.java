package com.lurdharry.book.book;

import lombok.Builder;

@Builder
public record BookResponse(
         String id,
         String title,
         String author,
         Integer quantity,
         Integer borrowedCopies,
         Integer availableCopies
) {
}
