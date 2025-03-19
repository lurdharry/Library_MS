package com.lurdharry.book.book;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String author;
    private Integer quantity;
    private Integer borrowedCopies;

    @Transient
    public Integer getAvailableCopies(){
        Integer a = quantity != null ? quantity :0;
        Integer b = borrowedCopies != null ? borrowedCopies :0;
        return a-b;
    }
}
