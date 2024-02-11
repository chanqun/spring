package com.example.springkafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class ClipProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RoutingKafkaTemplate routingKafkaTemplate;

    public ClipProducer(KafkaTemplate<String, String> kafkaTemplate, RoutingKafkaTemplate routingKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.routingKafkaTemplate = routingKafkaTemplate;
    }

    public void async(String topic, String message) {
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);

        send.thenRun(() -> System.out.println("Hello"));
    }

    public void sync(String topic, String message) {
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);
        try {
            System.out.println("success sync");
            send.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void routingSend(String topic, String message) {
        routingKafkaTemplate.send(topic, message);
    }
}
