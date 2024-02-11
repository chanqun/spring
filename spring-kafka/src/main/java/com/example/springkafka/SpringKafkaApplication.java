package com.example.springkafka;

import com.example.springkafka.producer.ClipProducer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class SpringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(ClipProducer clipProducer) {
        return args -> {
            clipProducer.async("clip3", "hello, async");
            clipProducer.sync("clip3", "hello, sync");
            Thread.sleep(3000);
        };
    }
}
