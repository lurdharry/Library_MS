package com.lurdharry.library.borrow;


import com.lurdharry.library.borrowline.BorrowLine;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "borrow_order"
)
public class BorrowOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String reference;

    private String borrowerId;


    @OneToMany(mappedBy = "order")
    private List<BorrowLine> borrowLines;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime borrowDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;
}
