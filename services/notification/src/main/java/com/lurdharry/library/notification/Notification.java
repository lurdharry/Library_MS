package com.lurdharry.library.notification;

import com.lurdharry.library.kafka.borrow.BorrowOrderConfirmation;
import com.lurdharry.library.kafka.borrow.BorrowStatusConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
@Getter
@Setter
public class Notification {
    @Id
    private String id;

    private NotificationType type;

    private LocalDateTime notificationDate;

    private BorrowOrderConfirmation orderConfirmation;

    private BorrowStatusConfirmation statusConfirmation;
}
