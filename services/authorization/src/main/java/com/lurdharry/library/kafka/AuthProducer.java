package com.lurdharry.library.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthProducer {
    private final KafkaTemplate<String, PasswordConfirmation> kafkaTemplate;

    public void sendChangePassword(PasswordConfirmation confirmation){
        log.info("Sending password change confirmation");
        Message<PasswordConfirmation> msg = MessageBuilder
                .withPayload(confirmation)
                .setHeader(KafkaHeaders.TOPIC,"order-status-topic")
                .build();
        kafkaTemplate.send(msg);
    }
}
