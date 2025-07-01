package com.lurdharry.library.kafka;

import com.lurdharry.library.kafka.borrow.BorrowOrderConfirmation;
import com.lurdharry.library.kafka.borrow.BorrowStatusConfirmation;
import com.lurdharry.library.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationService service;


    @KafkaListener(topics = "borrow-order-topic")
    public void consumeBorrowOrderNotification(BorrowOrderConfirmation confirmation){
        log.info("consuming message from borrow-order-topic {}",confirmation);
        service.handleBorrowOrderConfirmation(confirmation);
    }


    @KafkaListener(topics = "order-status-topic")
    public void consumeOrderStatusNotification(BorrowStatusConfirmation confirmation){
        log.info("consuming message from order-status-topic {}",confirmation);
        service.handleBorrowStatusConfirmation(confirmation);
    }
}
