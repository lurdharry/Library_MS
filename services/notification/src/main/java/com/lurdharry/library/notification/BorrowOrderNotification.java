package com.lurdharry.library.notification;

import com.lurdharry.library.kafka.borrow.BorrowOrderConfirmation;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Document(collection = "notifications")
@TypeAlias("borrowOrderNotification")
public class BorrowOrderNotification extends Notification{
    private BorrowOrderConfirmation orderConfirmation;
}
