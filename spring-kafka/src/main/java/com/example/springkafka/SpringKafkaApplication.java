package com.example.springkafka;

import com.example.springkafka.producer.ClipProducer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

@SpringBootApplication
public class SpringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(
            ClipProducer clipProducer, KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer
    ) {
        return args -> {
            kafkaMessageListenerContainer.start();

            clipProducer.async("clip4", "hello, clip4 container");
            Thread.sleep(3000);
        };
    }
}
