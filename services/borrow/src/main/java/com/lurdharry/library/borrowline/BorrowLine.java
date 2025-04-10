package com.lurdharry.library.borrowline;

import com.lurdharry.library.borrow.BorrowOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class BorrowLine {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "borrow_order_id")
    private BorrowOrder borrowOrder;

    private String bookId;
}
