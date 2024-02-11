package com.example.springkafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ClipProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ClipProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void async(String topic, String message) {
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);

        send.thenRun(() -> System.out.println("Hello"));
    }
}
