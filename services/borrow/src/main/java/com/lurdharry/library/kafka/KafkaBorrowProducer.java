package com.lurdharry.library.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaBorrowProducer {
    private final KafkaTemplate<String,BorrowOrderConfirmation> orderKafkaTemplate;
    private final KafkaTemplate<String,BorrowStatusConfirmation> statusKafkaTemplate;

    public void sendBorrowOrderConfirmation(BorrowOrderConfirmation confirmation){
        log.info("Producing BorrowOrderConfirmation for order {}",confirmation.bookOrderRef());
        Message<BorrowOrderConfirmation> msg = MessageBuilder
                .withPayload(confirmation)
                .setHeader(KafkaHeaders.TOPIC,"borrow-order-topic")
                .build();

        orderKafkaTemplate.send(msg);
    }

    public void sendStatusConfirmation(BorrowStatusConfirmation confirmation){
        log.info("Producing BorrowStatusConfirmation for order {}",confirmation.bookOrderRef());
        Message<BorrowStatusConfirmation> msg = MessageBuilder
                .withPayload(confirmation)
                .setHeader(KafkaHeaders.TOPIC,"order-status-topic")
                .build();

        statusKafkaTemplate.send(msg);
    }

}
