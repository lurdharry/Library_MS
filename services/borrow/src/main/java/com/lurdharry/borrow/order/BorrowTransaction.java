package com.lurdharry.borrow.order;


import com.lurdharry.borrow.borrowline.BorrowLine;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;


public class BorrowTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String borrowerId;

    private List<BorrowLine> borrowLines;

    @CreatedDate
    private LocalDateTime borrowDate;

    @LastModifiedDate
    private LocalDateTime lastModified;
}
