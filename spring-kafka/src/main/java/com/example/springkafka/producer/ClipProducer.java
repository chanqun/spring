package com.example.springkafka.producer;

import com.example.springkafka.model.Animal;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ClipProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Animal> kafkaJsonTemplate;

    public ClipProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, Animal> kafkaJsonTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaJsonTemplate = kafkaJsonTemplate;
    }

    public void async(String topic, String message) {
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);

        send.thenRun(() -> System.out.println("Hello"));
    }

    public void async(String topic, Animal animal) {
        CompletableFuture<SendResult<String, Animal>> send = kafkaJsonTemplate.send(topic, animal);

        send.thenRun(() -> System.out.println("Hello"));
    }
}
