package com.example.springkafka.consumer;

import com.example.springkafka.model.Animal;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class ClipConsumer {

    @KafkaListener(id = "clip4-listener-id", topics = "clip4-listener")
    public void listen(String message,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
                       ConsumerRecordMetadata metadata) {
        System.out.println("listener listen " + message + " metadata : " + metadata.offset());
        System.out.println("timestamp : " + timestamp);
    }

    @KafkaListener(id = "clip4-animal-listener", topics = "clip4-animal")
    public void listenAnimal(Animal animal) {
        System.out.println("animal : " + animal);
    }
}
