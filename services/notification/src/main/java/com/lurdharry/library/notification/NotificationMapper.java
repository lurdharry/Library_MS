package com.lurdharry.library.notification;

import com.lurdharry.library.kafka.borrow.BorrowOrderConfirmation;
import com.lurdharry.library.kafka.borrow.BorrowStatusConfirmation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationMapper {
    public Notification toOrderNotification(BorrowOrderConfirmation confirmation) {
       return Notification.builder()
                .type(NotificationType.BORROW_ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(confirmation)
                .build();
    }

    public Notification toStatusNotification(BorrowStatusConfirmation confirmation) {
        return Notification.builder()
                .type(NotificationType.BORROW_STATUS_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .statusConfirmation(confirmation)
                .build();
    }
}
