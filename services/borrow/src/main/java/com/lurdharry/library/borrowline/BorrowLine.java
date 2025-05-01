package com.lurdharry.library.borrowline;

import com.lurdharry.library.borrow.BorrowOrder;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class BorrowLine {
    @Id
    @GeneratedValue
    private String id;

    @ManyToOne
    @JoinColumn(name = "borrow_order_id")
    private BorrowOrder borrowOrder;

    private String bookId;
}
