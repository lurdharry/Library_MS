package com.lurdharry.library.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<String, BorrowOrderConfirmation> borrowOrderKafkaTemplate(
            ProducerFactory<String, BorrowOrderConfirmation> pf) {
        return new KafkaTemplate<>(pf);
    }

    @Bean
    public KafkaTemplate<String, BorrowStatusConfirmation> borrowStatusKafkaTemplate(
            ProducerFactory<String, BorrowStatusConfirmation> pf) {
        return new KafkaTemplate<>(pf);
    }

}
